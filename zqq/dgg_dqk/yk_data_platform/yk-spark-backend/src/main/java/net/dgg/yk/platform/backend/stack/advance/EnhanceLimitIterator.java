package net.dgg.yk.platform.backend.stack.advance;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.utils.CloseableUtils;

import java.io.Closeable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Function;

@Slf4j
public class EnhanceLimitIterator<D> implements Iterator<D> {

    private Iterator<D> it;
    private int limit;
    private Closeable[] closeables;
    private boolean released = false;
    private int count = 0;
    private Function<Integer, IteratorSource<D>> iteratorFactory;

    public EnhanceLimitIterator(Iterator<D> it, int limit, Closeable[] closeables, Function<Integer, IteratorSource<D>> iteratorFactory) {
        this.it = it;
        this.limit = limit;
        this.closeables = closeables;
        this.iteratorFactory = iteratorFactory;
    }

    @Override
    public boolean hasNext() {
        if (count >= limit) {
            release();
            return false;
        } else if (released) {
            return false;
        } else {
            int reTryCount = 0;
            while (reTryCount <= 20) {
                reTryCount++;
                try {
                    boolean hasNext = it.hasNext();
                    if (hasNext) {
                        return true;
                    } else {
                        release();
                        return false;
                    }
                } catch (Exception e) {
                    log.error("获取是否有更多元素时发生错误", e);
                    initIterator();
                }
            }
            release();
            throw new IllegalStateException("发生致使错误，不得不终止任务！");
        }
    }

    public void release() {
        if (!released) {
            log.info("释放资源");
            for (Closeable closeable : closeables) {
                CloseableUtils.closeQuietly(closeable);
            }
            released = true;
        }
    }

    @Override
    public D next() {
        int reTryCount = 0;
        while (reTryCount <= 20) {
            reTryCount++;
            try {
                D doc = this.it.next();
                count = count++;
                if (count >= limit) {
                    release();
                }
                return doc;
            } catch (Exception e) {
                log.error("发生读取错误，将重新构建", e);
                initIterator();
            }
        }
        throw new IllegalStateException("发生致使错误，不得不终止");
    }

    public void initIterator() {
        log.info("准备重新初始化迭代器");
        Lists.newArrayList(this.closeables).forEach(CloseableUtils::closeQuietly);
        IteratorSource<D> iteratorSource = iteratorFactory.apply(count);
        this.it = iteratorSource.getIterator();
        this.closeables = iteratorSource.getCloseables();
    }
}

package net.dgg.yk.platform.backend.stack.advance;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.utils.CloseableUtils;

import java.io.Closeable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

@Slf4j
public class EnhanceIterator<D> implements Iterator<D> {

    private Integer reTryLimit;

    private Integer limit;

    private Iterator<D> iterator;

    private Function<Integer, IteratorSource<D>> factory;

    private Closeable[] closeables;

    private Integer offset = 0;

    private boolean released = false;

    public EnhanceIterator(Iterator<D> iterator, Closeable[] closeables, Function<Integer, IteratorSource<D>> factory, Integer reTryLimit) {
        this.iterator = iterator;
        this.closeables = closeables;
        this.factory = factory;
        this.offset = 0;
        this.limit = -1;
        this.reTryLimit = reTryLimit;
    }

    public EnhanceIterator(Iterator<D> iterator, Closeable[] closeables, Function<Integer, IteratorSource<D>> factory) {
        this.iterator = iterator;
        this.closeables = closeables;
        this.factory = factory;
        this.offset = 0;
        this.limit = -1;
        this.reTryLimit = 20;
    }

    public EnhanceIterator(Iterator<D> iterator, Integer limit, Closeable[] closeables, Function<Integer, IteratorSource<D>> factory) {
        this.iterator = iterator;
        this.closeables = closeables;
        this.factory = factory;
        this.offset = 0;
        this.limit = limit;
        this.reTryLimit = 20;
    }

    public EnhanceIterator(Iterator<D> iterator, Integer limit, Closeable[] closeables, Function<Integer, IteratorSource<D>> factory, Integer reTryLimit) {
        this.iterator = iterator;
        this.closeables = closeables;
        this.factory = factory;
        this.offset = 0;
        this.limit = limit;
        this.reTryLimit = reTryLimit;
    }

    @Override
    public D next() {
        if (!released) {
            if (limit > -1) {
                if (offset < limit) {
                    Integer reTry = 0;
                    while (reTry++ < reTryLimit) {
                        try {
                            D d = iterator.next();
                            offset++;
                            return d;
                        } catch (NoSuchElementException e) {
                            release();
                            throw new IllegalStateException(e);
                        } catch (Exception e) {
                            initIterator();
                        }
                    }
                    release();
                    throw new IllegalStateException("你达到了重试次数上限：" + reTryLimit);
                } else {
                    release();
                    throw new NoSuchElementException("你访问的元素达到了最大上限：" + limit);
                }
            } else {
                int reTryCount = 0;
                while (reTryCount++ <= reTryLimit) {
                    try {
                        D d = this.iterator.next();
                        offset++;
                        return d;
                    } catch (NoSuchElementException e) {
                        release();
                        throw e;
                    } catch (Exception e) {
                        initIterator();
                    }
                }
                release();
                throw new IllegalStateException("你的重试次数达到上限：" + reTryLimit);
            }
        } else {
            throw new IllegalStateException("非法访问该迭代器");
        }
    }

    @Override
    public boolean hasNext() {
        if (released) {
            return false;
        } else {
            if (limit > -1) {
                if (offset < limit) {
                    int reTryCount = 0;
                    while (reTryCount++ <= reTryLimit) {
                        try {
                            boolean next = this.iterator.hasNext();
                            if (!next) {
                                release();
                            }
                            return next;
                        } catch (Exception e) {
                            initIterator();
                        }
                    }
                    release();
                    throw new IllegalStateException("发生严重错误!");
                } else {
                    release();
                    return false;
                }
            } else {
                int reTryCount = 0;
                while (reTryCount++ <= reTryLimit) {
                    try {
                        boolean next = this.iterator.hasNext();
                        if (!next) {
                            release();
                        }
                        return next;
                    } catch (Exception e) {
                        initIterator();
                    }
                }
                release();
                throw new IllegalStateException("发生严重错误!");
            }
        }
    }

    public void release() {
        if (!released) {
            log.info("本迭代器读取{}", offset);
            Lists.newArrayList(this.closeables).forEach(CloseableUtils::closeQuietly);
            released = true;
        }
    }

    public void initIterator() {
        Lists.newArrayList(this.closeables).forEach(CloseableUtils::closeQuietly);
        IteratorSource<D> iteratorSource = factory.apply(offset);
        this.iterator = iteratorSource.getIterator();
        this.closeables = iteratorSource.getCloseables();
    }

}

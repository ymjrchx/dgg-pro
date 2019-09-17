package net.dgg.core.redis;

import net.dgg.core.utils.exception.DggFrameworkException;

/**
 * @Desc Redis异常处理类
 * @Date 17:06 2018/9/11
 * @Author Li Xingjiang
 **/
public class DggRedisExeption extends RuntimeException {
    public DggRedisExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public DggRedisExeption(Throwable cause) {
        super(cause);
    }

    public DggRedisExeption(String message) {
        super(message);
    }

    public DggRedisExeption() {
        super();
    }
}
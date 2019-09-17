package net.dgg.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Desc:   Spring封装异常处理类
 * Author: Li Xingjiang
 * Date:   2018/9/11 10:42
 * Version: 1.0
 **/
public class DggCoreExeption extends Exception {

    public DggCoreExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public DggCoreExeption(Throwable cause) {
        super(cause);
    }

    public DggCoreExeption(String message) {
        super(message);
    }

    public DggCoreExeption() {
        super();
    }
}
package net.dgg.bigdata.manager.annotation;

import java.lang.annotation.*;

/**
 * Created by wu on 2018-03-13.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthOpt {

    /**
     * 操作权限代码
     *
     * @return
     */
    String code() default "";
}

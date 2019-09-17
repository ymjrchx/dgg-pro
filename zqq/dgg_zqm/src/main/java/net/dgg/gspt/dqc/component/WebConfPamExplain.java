package net.dgg.gspt.dqc.component;

import java.lang.annotation.*;

/**
 * @author 刘阳
 * @ClassName <WebConPamExplain>
 * @despriction
 * @create 2018/08/10 11:37
 **/
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebConfPamExplain {
    String value() default "";
}

package net.dgg.core.mq.annotation;

import net.dgg.core.spring.DggWebConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CoreMqConfiguration.class})
@Documented
@Inherited
public @interface EnableCoreMq {
}

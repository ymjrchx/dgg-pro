package net.dgg.core.spring.annotation;

import net.dgg.core.spring.DggWebConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SpringCoreConfiguration.class, DggWebConfig.class})
@Documented
@Inherited
public @interface EnableCoreSpring {
}

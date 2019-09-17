package net.dgg.core.es.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CoreEsConfiguration.class})
@Documented
@Inherited
public @interface EnableCoreEs {
}

package net.dgg.core.mongo.annotation;

import net.dgg.core.spring.DggWebConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CoreMongoConfiguration.class})
@Documented
@Inherited
public @interface EnableCoreMongo {
}

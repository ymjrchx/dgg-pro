package net.dgg.core.message.annotation;

import net.dgg.core.mq.annotation.CoreMqConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CoreMessageConfiguration.class, CoreMqConfiguration.class})
@Documented
@Inherited
public @interface EnableCoreMessage {
}

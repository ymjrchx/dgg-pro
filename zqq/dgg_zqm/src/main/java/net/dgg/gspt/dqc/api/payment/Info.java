package net.dgg.gspt.dqc.api.payment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 李程 on 2017/10/19.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Info {

    String value();

}

package net.dgg.base.baseDao;

import java.lang.annotation.*;

/**
 * @author 刘阳
 * @ClassName <Table>
 * @despriction
 * @create 2018/12/05 9:20
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    /**
     * 表名
     *
     * @return
     */
    String name();

    /**
     * Id 名
     *
     * @return
     */
    String idName() default "id";

}

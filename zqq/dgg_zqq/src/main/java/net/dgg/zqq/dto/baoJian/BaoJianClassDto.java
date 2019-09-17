package net.dgg.zqq.dto.baoJian;

import lombok.Data;

/**
 * @author 刘阳
 * @ClassName <BaoJianClassDto>
 * @despriction 商标报件3级分类
 * @create 2018/11/06 16:55
 **/
@Data
public class BaoJianClassDto {

    /**
     * 三级信息
     */
    private String code;
    private String name;

    /**
     * 一级级信息
     */
    private String firstCode;
    private String firstName;

    /**
     * 二级信息
     */
    private String secondCode;
    private String secondName;


}

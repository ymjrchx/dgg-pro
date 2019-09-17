package net.dgg.gspt.dqc.dto.baoJian;

import lombok.Data;

/**
 * @author 刘阳
 * @ClassName <BaoJianDto>
 * @despriction 报件DTO
 * @create 2018/11/06 16:49
 **/
@Data
public class BaoJianDto {

    /**
     * 申请人类型 0:法人或其他组织；1:自然人；
     */
    private String tmzcrAppTypeId;

    /**
     * 书式类型（国籍或地区） 0:中国大陆；1：国外；2：中国台湾；3：中国香港；4：中国澳门；
     */
    private String tmzcrAppGjdq;

    /**
     * 代理委托书
     */
    private String fileWtKey;

    /**
     * 申请人名称
     */
    private String appCnName;

    /**
     * 申请人地址
     */
    private String appCnAddr;

    /**
     * (0是一般, 1是集体，2是证明)
     */
    private String brandType;

    /**
     * 商标说明
     */
    private String tmDesignDeclare;

    /**
     * 优先权声明 0：无；1：在先优先权；2：展会优先权；
     */
    private String priorityType;

    /**
     * 报件分类[{code:'尼斯编号',name:'名字'}]
     */
    String goods;
    /**
     * 彩色商标图样
     */
    private String tMimgColorKey;

    /**
     * 黑白商标图样
     */
    private String tMimgBlackKey;

    /**
     * 肖像商标公证文件
     */
    private String tMimgMenKey;

    /**
     * 有关说明文件
     */
    private String fileYgKey;


    /**
     * 法人或其他组织
     */


    /**
     * 联系人
     */
    private String appContactPerson;

    /**
     * 联系电话
     */
    private String appContactTel;

    /**
     * 邮政编码
     */
    private String appContactZip;

    /**
     * 主体资格证明文件(中文)
     */
    private String fileZtKey;


    /**
     * 自然人
     */

    /**
     * 证件名称 000001，身份证；000002，护照；000004，其他
     */
    private String appCardName;

    /**
     * 证件号码
     */
    private String appCartNum;


    /**
     * 身份证明文件(中文)
     */
    private String fileSfKey;

    /**
     * 优先权证明文件
     */
    private String fileYxKey;

    /**
     * 申请/展出国家/地区
     */
    private String baseCrty;

    /**
     * 申请/展出日期
     */
    private String exhibitionDate;

    /**
     * 申请号
     */
    private String priorityNum;

    /**
     * 来源
     */
    private String source;
}

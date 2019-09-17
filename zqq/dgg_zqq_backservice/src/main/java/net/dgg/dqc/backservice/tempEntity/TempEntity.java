
package net.dgg.dqc.backservice.tempEntity;

import net.dgg.dqc.backservice.entity.MongoBaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * @author 刘阳
 * @ClassName <TempEntity>
 * @despriction 新json实体
 * @create 2018/08/14 10:21
 **/
@Document(collection = "quanchacha_detail_results")
public class TempEntity extends MongoBaseEntity {


    /**
     * head : {"userNameEn":"","userAddress":"河北省保定市涿州市范阳西路189号","imgUrl":"http://tm-image.qichacha.com/3fb52045c9c5aeda28ed94302702348b.jpg","imgLocal":{"clName":"d1fd6b6d549fe625615b3d6f590c8227@head#imgLocal@http~！！tm-image.qichacha.com！3fb52045c9c5aeda28ed94302702348b.jpg","clUrl":"http://tm-image.qichacha.com/3fb52045c9c5aeda28ed94302702348b.jpg","clPath":null},"userAddressEn":"","sbType":"第9类-科学仪器","userName":"中国石油集团东方地球物理勘探有限责任公司"}
     * isCrawlImg : true
     * sbggProcess : ["2016-01-27 第1489期 商标初步审定公告"]
     * loadTime : 2018-09-12T14:36:06
     * serviceItem : {"goods":"0901-计算圆尺 0901-电脑软件 0901-磁盘 0901-电子出版物 0901-计算机程序 0901-磁性数据介质 0901-已录制的计算机操作程序 0901-光盘 0901-计算机软件 0901-已录制的计算机程序","goodsNum":"0901"}
     * sbNum : 14259541
     * sbProcess : ["2016-04-27 驳回复审注册公告排版完成","2015-12-16 驳回复审评审实审裁文发文","2015-02-16 驳回复审中","2015-02-08 商标注册申请驳回通知发文","2014-03-27 商标注册申请中"]
     * state : 驳回复审中
     * sbName : KLSEIS
     * applicationDate : 2014-03-27
     * sbRegisterData : {"sbType":"","registerSbggTime":"2016-04-28","agency":"北京戈程知识产权代理有限公司","color":"","manageTime":"2016-04-28至2026-04-27","lateSpecifiedDate":null,"registerSbggNum":"1501","firstSbggNum":"1489","priority":null,"overseasDate":null,"firstSbggTime":"2016-01-27","otherUser":"否"}
     */
    private HeadEntity head;
    private Boolean isCrawlImg;
    private List<String> sbggProcess;
    private String loadTime;
    private ServiceItemEntity serviceItem;
    private String sbNum;
    private List<String> sbProcess;
    private String state;
    private String sbName;
    private String applicationDate;
    private SbRegisterDataEntity sbRegisterData;

    public void setHead(HeadEntity head) {
        this.head = head;
    }

    public void setIsCrawlImg(Boolean isCrawlImg) {
        this.isCrawlImg = isCrawlImg;
    }

    public void setSbggProcess(List<String> sbggProcess) {
        this.sbggProcess = sbggProcess;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public void setServiceItem(ServiceItemEntity serviceItem) {
        this.serviceItem = serviceItem;
    }

    public void setSbNum(String sbNum) {
        this.sbNum = sbNum;
    }

    public void setSbProcess(List<String> sbProcess) {
        this.sbProcess = sbProcess;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setSbRegisterData(SbRegisterDataEntity sbRegisterData) {
        this.sbRegisterData = sbRegisterData;
    }

    public HeadEntity getHead() {
        return head;
    }

    public Boolean isIsCrawlImg() {
        return isCrawlImg;
    }

    public List<String> getSbggProcess() {
        return sbggProcess;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public ServiceItemEntity getServiceItem() {
        return serviceItem;
    }

    public String getSbNum() {
        return sbNum;
    }

    public List<String> getSbProcess() {
        return sbProcess;
    }

    public String getState() {
        return state;
    }

    public String getSbName() {
        return sbName;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public SbRegisterDataEntity getSbRegisterData() {
        return sbRegisterData;
    }

    public class HeadEntity {
        /**
         * userNameEn :
         * userAddress : 河北省保定市涿州市范阳西路189号
         * imgUrl : http://tm-image.qichacha.com/3fb52045c9c5aeda28ed94302702348b.jpg
         * imgLocal : {"clName":"d1fd6b6d549fe625615b3d6f590c8227@head#imgLocal@http~！！tm-image.qichacha.com！3fb52045c9c5aeda28ed94302702348b.jpg","clUrl":"http://tm-image.qichacha.com/3fb52045c9c5aeda28ed94302702348b.jpg","clPath":null}
         * userAddressEn :
         * sbType : 第9类-科学仪器
         * userName : 中国石油集团东方地球物理勘探有限责任公司
         */
        private String userNameEn;
        private String userAddress;
        private String imgUrl;
        private ImgLocalEntity imgLocal;
        private String userAddressEn;
        private String sbType;
        private String userName;

        public void setUserNameEn(String userNameEn) {
            this.userNameEn = userNameEn;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setImgLocal(ImgLocalEntity imgLocal) {
            this.imgLocal = imgLocal;
        }

        public void setUserAddressEn(String userAddressEn) {
            this.userAddressEn = userAddressEn;
        }

        public void setSbType(String sbType) {
            this.sbType = sbType;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserNameEn() {
            return userNameEn;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public ImgLocalEntity getImgLocal() {
            return imgLocal;
        }

        public String getUserAddressEn() {
            return userAddressEn;
        }

        public String getSbType() {
            return sbType;
        }

        public String getUserName() {
            return userName;
        }

        public class ImgLocalEntity {
            /**
             * clName : d1fd6b6d549fe625615b3d6f590c8227@head#imgLocal@http~！！tm-image.qichacha.com！3fb52045c9c5aeda28ed94302702348b.jpg
             * clUrl : http://tm-image.qichacha.com/3fb52045c9c5aeda28ed94302702348b.jpg
             * clPath : null
             */
            private String clName;
            private String clUrl;
            private String clPath;

            public void setClName(String clName) {
                this.clName = clName;
            }

            public void setClUrl(String clUrl) {
                this.clUrl = clUrl;
            }

            public void setClPath(String clPath) {
                this.clPath = clPath;
            }

            public String getClName() {
                return clName;
            }

            public String getClUrl() {
                return clUrl;
            }

            public String getClPath() {
                return clPath;
            }
        }
    }

    public class ServiceItemEntity {
        /**
         * goods : 0901-计算圆尺 0901-电脑软件 0901-磁盘 0901-电子出版物 0901-计算机程序 0901-磁性数据介质 0901-已录制的计算机操作程序 0901-光盘 0901-计算机软件 0901-已录制的计算机程序
         * goodsNum : 0901
         */
        private String goods;
        private String goodsNum;

        public void setGoods(String goods) {
            this.goods = goods;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getGoods() {
            return goods;
        }

        public String getGoodsNum() {
            return goodsNum;
        }
    }

    public class SbRegisterDataEntity {
        /**
         * sbType :
         * registerSbggTime : 2016-04-28
         * agency : 北京戈程知识产权代理有限公司
         * color :
         * manageTime : 2016-04-28至2026-04-27
         * lateSpecifiedDate : null
         * registerSbggNum : 1501
         * firstSbggNum : 1489
         * priority : null
         * overseasDate : null
         * firstSbggTime : 2016-01-27
         * otherUser : 否
         */
        private String sbType;
        private String registerSbggTime;
        private String agency;
        private String color;
        private String manageTime;
        private String lateSpecifiedDate;
        private String registerSbggNum;
        private String firstSbggNum;
        private String priority;
        private String overseasDate;
        private String firstSbggTime;
        private String otherUser;

        public void setSbType(String sbType) {
            this.sbType = sbType;
        }

        public void setRegisterSbggTime(String registerSbggTime) {
            this.registerSbggTime = registerSbggTime;
        }

        public void setAgency(String agency) {
            this.agency = agency;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setManageTime(String manageTime) {
            this.manageTime = manageTime;
        }

        public void setLateSpecifiedDate(String lateSpecifiedDate) {
            this.lateSpecifiedDate = lateSpecifiedDate;
        }

        public void setRegisterSbggNum(String registerSbggNum) {
            this.registerSbggNum = registerSbggNum;
        }

        public void setFirstSbggNum(String firstSbggNum) {
            this.firstSbggNum = firstSbggNum;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public void setOverseasDate(String overseasDate) {
            this.overseasDate = overseasDate;
        }

        public void setFirstSbggTime(String firstSbggTime) {
            this.firstSbggTime = firstSbggTime;
        }

        public void setOtherUser(String otherUser) {
            this.otherUser = otherUser;
        }

        public String getSbType() {
            return sbType;
        }

        public String getRegisterSbggTime() {
            return registerSbggTime;
        }

        public String getAgency() {
            return agency;
        }

        public String getColor() {
            return color;
        }

        public String getManageTime() {
            return manageTime;
        }

        public String getLateSpecifiedDate() {
            return lateSpecifiedDate;
        }

        public String getRegisterSbggNum() {
            return registerSbggNum;
        }

        public String getFirstSbggNum() {
            return firstSbggNum;
        }

        public String getPriority() {
            return priority;
        }

        public String getOverseasDate() {
            return overseasDate;
        }

        public String getFirstSbggTime() {
            return firstSbggTime;
        }

        public String getOtherUser() {
            return otherUser;
        }
    }
}
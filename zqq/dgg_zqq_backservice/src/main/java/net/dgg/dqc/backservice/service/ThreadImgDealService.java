package net.dgg.dqc.backservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 刘阳
 * @ClassName <ThreadImgDealService>
 * @despriction 图片处理线程
 * @create 2018/09/20 15:43
 **/
@Service
public class ThreadImgDealService {
    @Lazy
    @Autowired
    private ImgDealService imgDealService;

    /**
     * 独立线程处理
     */
    @Async("processExecutor")
    public void threadImgDeal(int startIndex, int endIndex) {
        imgDealService.dealIndexStartTo(startIndex, endIndex);
    }

    @Async("processExecutor")
    public void threadDealFailRecord(int startIndex, int endIndex){
        imgDealService.dealFailRecordIndexStartTo(startIndex, endIndex);
    }

}

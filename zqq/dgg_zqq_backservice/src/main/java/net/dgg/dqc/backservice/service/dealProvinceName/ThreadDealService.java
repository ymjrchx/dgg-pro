package net.dgg.dqc.backservice.service.dealProvinceName;

import net.dgg.dqc.backservice.entity.dealPorvinceName.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 刘阳
 * @ClassName <ThreadDealService>
 * @despriction
 * @create 2018/07/13 13:44
 **/
@Service
public class ThreadDealService {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    @Lazy
    private DealService dealService;


    @Async("processExecutor")
    public void crateThreadAndStart(List<Address> addressList) {
        Thread th = Thread.currentThread();
        //System.out.println(th.getName() + "线程执行");

        for (Address address : addressList) {
            if (address == null) {
                continue;
            }
            address.setProvince(this.provinceService.getAreaString(address.getDengji_zhusuo()));
            this.dealService.updateProvence(address);
        }

    }


}

package net.dgg.dqc.backservice.quartz.dealProvinceName;

import net.dgg.dqc.backservice.entity.dealPorvinceName.Address;
import net.dgg.dqc.backservice.service.dealProvinceName.ThreadDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author 刘阳
 * @ClassName <DealTask>
 * @despriction
 * @create 2018/07/13 10:51
 **/
@Component
public class DealTask {

    private boolean start = false;

    public int bath = 100;

    private ConcurrentLinkedDeque<Address> addresses = new ConcurrentLinkedDeque<>();

    @Autowired
    private ThreadDealService threadDealService;


    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    private List<Address> getAddressList(int i) {
        List<Address> addressList = new ArrayList(i);
        if (i <= 0 || this.addresses.isEmpty()) {
            return addressList;
        }
        for (int j = 0; j < i; j++) {
            Address address = null;
            try {
                address = this.addresses.pollLast();
            } catch (Exception e) {
                break;
            }
            if (address == null) {
                continue;
            }
            addressList.add(address);
        }
        return addressList;
    }


    //@Scheduled(cron = "0/1 * *  * * ? ")
    public void dealTash() {
        //System.out.println("定时器执行");
        if (!this.start || this.addresses.isEmpty()) {
            return;
        }
        for (int i = 0; i < 100; i++) {
            List<Address> addressList = this.getAddressList(bath);
            threadDealService.crateThreadAndStart(addressList);
        }

    }


    public int getSize() {
        return this.addresses.size();
    }

    public void setStart(boolean start) {
        this.start = start;
    }


}

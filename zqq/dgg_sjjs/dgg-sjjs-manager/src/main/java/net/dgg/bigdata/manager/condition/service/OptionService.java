package net.dgg.bigdata.manager.condition.service;

import net.dgg.base.baseService.BaseService;
import net.dgg.bigdata.common.entity.condition.Input;
import net.dgg.bigdata.common.entity.condition.Option;
import net.dgg.bigdata.manager.condition.dao.OptionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/11 13:55
 * @Description:
 */
@Service
public class OptionService extends BaseService<Option> {

    @Resource
    private OptionDao optionDao;

    List<Option> getOptionByTreeBookId(Long actionId) {
        return optionDao.getOptionByTreeBookId(actionId);
    }
}

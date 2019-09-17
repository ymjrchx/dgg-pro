package net.dgg.bigdata.manager.condition.service;

import net.dgg.base.baseService.BaseService;
import net.dgg.bigdata.common.entity.condition.Input;
import net.dgg.bigdata.manager.condition.dao.InputDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/11 13:54
 * @Description:
 */
@Service
public class InputService extends BaseService<Input> {


    @Resource
    private InputDao inputDao;

    List<Input> getInputByTreeBookId(Long treeBookId) {
        return inputDao.getInputByTreeBookId(treeBookId);
    }
}

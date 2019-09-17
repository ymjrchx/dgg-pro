package net.dgg.bigdata.manager.condition.service;

import net.dgg.base.baseService.BaseService;
import net.dgg.bigdata.common.entity.condition.Action;
import net.dgg.bigdata.manager.condition.dao.ActionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/11 13:32
 * @Description:
 */
@Service
public class ActionService extends BaseService<Action> {

    @Resource
    private ActionDao actionDao;

    List<Action> getActionByTreeBookId(Long treeBookId){
        return actionDao.getActionByTreeBookId(treeBookId);
    }
}

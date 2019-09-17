package net.dgg.zqq.services.order;

import net.dgg.zqq.dao.order.AssessDao;
import net.dgg.zqq.entity.order.Assess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AssessService
 * @Description: TODO
 * @Author: huangL
 * @Date: 2019/1/14 16:40
 */
@Service
public class AssessService {
    @Autowired
    private AssessDao assessDao;

    public List<Assess> query(Map map) {
        return assessDao.query(map);
    }
}

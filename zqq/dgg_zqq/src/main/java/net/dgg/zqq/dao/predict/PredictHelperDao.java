package net.dgg.zqq.dao.predict;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/10/17.
 */
@Repository
public interface PredictHelperDao {

    //查询分类信息，根据行业
    List<Map<String, Object>> queryByIndustryId(String industryId);

    //查询所有行业列表
    List<Map<String, Object>> queryAllIndustry();

}

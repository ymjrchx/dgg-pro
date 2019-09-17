package net.dgg.bigdata.sjjs.web.condition.dao;

import net.dgg.bigdata.common.entity.condition.IndustryModel;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/5 15:03
 * @Description:
 */
@Repository
public interface IndustryModelDao {

    /**
     * 获取行业模板
     */
    @Select("select id,applicable_industry_name as applicableIndustryName,sort from yk_industry_model order by sort ")
    List<IndustryModel> getIndustryModel();
}

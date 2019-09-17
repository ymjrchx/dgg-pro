package net.dgg.bigdata.sjjs.web.condition.service;

import com.alibaba.fastjson.JSON;
import net.dgg.base.baseService.BaseService;
import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.entity.condition.IndustryModel;
import net.dgg.bigdata.sjjs.web.condition.dao.IndustryModelDao;
import net.dgg.bigdata.sjjs.web.condition.dto.ConditionGroupDto;
import net.dgg.bigdata.sjjs.web.condition.dto.IndustryModelDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/18 10:21
 * @Description:
 */
@Service
public class IndustryModelService extends BaseService<IndustryModel> {

    @Resource
    private ConditionGroupsService conditionGroupsService;

    @Resource
    private IndustryModelDao industryModelDao;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取行业模板
     *
     * @return
     */
    public List<IndustryModelDto> getIndustryModel() {
        List<IndustryModelDto> list = new ArrayList<>();
        List<IndustryModel> querys = industryModelDao.getIndustryModel();
        IndustryModelDto industryModelDto = null;
        for (IndustryModel industryModel : querys) {
            industryModelDto = new IndustryModelDto();
            industryModelDto.setApplicable_industry_name(industryModel.getApplicableIndustryName());
            industryModelDto.setOrder(industryModel.getSort());
            List<ConditionGroupDto> condition_groups = conditionGroupsService.getConditionGroupDtoForIndustry(industryModel.getId());
            industryModelDto.setCondition_groups(condition_groups);
            list.add(industryModelDto);
        }
        return list;
    }

    public List<IndustryModelDto> getRedisIndustryModel() throws Exception{
        List<IndustryModelDto> list = null;
        String key = ConditionConstant.INDUSTRY_REDIS_KEY;
        if(redisTemplate.hasKey(key)){
            list = JSON.parseArray((String) redisTemplate.opsForValue().get(key),IndustryModelDto.class);
        }else{
            list = getIndustryModel();
            redisTemplate.opsForValue().set(key,JSON.toJSONString(list),60*60*24*30, TimeUnit.SECONDS);
        }
        return list;
    }

}

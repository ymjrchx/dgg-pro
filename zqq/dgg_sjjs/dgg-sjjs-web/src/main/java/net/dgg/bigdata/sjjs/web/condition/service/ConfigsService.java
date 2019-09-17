package net.dgg.bigdata.sjjs.web.condition.service;

import com.alibaba.fastjson.JSON;
import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.entity.TreeBook;
import net.dgg.bigdata.common.entity.condition.Action;
import net.dgg.bigdata.sjjs.web.condition.dao.ActionDao;
import net.dgg.bigdata.sjjs.web.condition.dao.InputDao;
import net.dgg.bigdata.sjjs.web.condition.dao.OptionDao;
import net.dgg.bigdata.sjjs.web.condition.dto.*;
import net.dgg.bigdata.sjjs.web.condition.entity.Configs;
import net.dgg.bigdata.sjjs.web.condition.entity.FilterOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 16:20
 * @Description:
 */
@Service
public class ConfigsService {

    @Autowired
    private TreeBookService treeBookService;

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private InputDao inputDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 将分类信息缓存在redis中
     *
     * @return
     */
    public Configs getConfigs() {

        Configs configs = new Configs();
        try {
            if (redisTemplate.hasKey(ConditionConstant.CONFIGS_REDIS_KEY)) {
                configs = JSON.parseObject(redisTemplate.opsForValue().get(ConditionConstant.CONFIGS_REDIS_KEY) + "", Configs.class);
            } else {
                List<CategorieDto> categorieDtos = getCategorieDto();
                configs.setCategories(categorieDtos);

                List<String> hots = getHot();
                configs.setHot_list(hots);

                Map<String, Object> inputValues = getInputValueDto();
                configs.setInput_values(inputValues);

                List<FilterOption> filterOptions = getFilterOptions();
                configs.setFilter_options(filterOptions);

                redisTemplate.opsForValue().set(ConditionConstant.CONFIGS_REDIS_KEY, JSON.toJSONString(configs), 60 * 60 * 24, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            List<CategorieDto> categorieDtos = getCategorieDto();
            configs.setCategories(categorieDtos);

            List<String> hots = getHot();
            configs.setHot_list(hots);

            Map<String, Object> inputValues = getInputValueDto();
            configs.setInput_values(inputValues);

            List<FilterOption> filterOptions = getFilterOptions();
            configs.setFilter_options(filterOptions);
        }
        return configs;
    }

    /**
     * 查询获取分类信息
     *
     * @return
     */
    public List<CategorieDto> getCategorieDto() {
        //查询所有分类
        List<TreeBook> treeBooks = treeBookService.getTreeBookListByCodeToDB(ConditionConstant.CONDITION_CODE,
                ConditionConstant.INT_1, ConditionConstant.INT_2, null);
        List<CategorieDto> categorieDtos = new ArrayList<>();
        CategorieDto categorieDto = null;
        List<String> dimensions = null;
        for (TreeBook treeBook : treeBooks) {
            //是一级分类
            if (ConditionConstant.CONDITION_CODE.equals(treeBook.getPcode())) {
                categorieDto = new CategorieDto();
                categorieDto.setName(treeBook.getName());
                //查询一级分类下的二级分类
                dimensions = new ArrayList<>();
                for (TreeBook nodeTreeBook : treeBooks) {
                    if (treeBook.getCode().equals(nodeTreeBook.getPcode())) {
                        dimensions.add(nodeTreeBook.getCode());
                    }
                }
                categorieDto.setDimensions(dimensions);
                categorieDtos.add(categorieDto);
            }
        }
        return categorieDtos;
    }

    /**
     * 查询获取热门
     *
     * @return
     */
    public List<String> getHot() {
        //查询所有分类
        List<TreeBook> treeBooks = treeBookService.getTreeBookListByCodeToDB(ConditionConstant.CONDITION_CODE,
                ConditionConstant.INT_1, ConditionConstant.INT_2, null);
        List<String> hotList = new ArrayList<>();
        for (TreeBook treeBook : treeBooks) {
            if (!ConditionConstant.CONDITION_CODE.equals(treeBook.getPcode()) && ConditionConstant.HOT.equals(treeBook.getExt2())) {
                hotList.add(treeBook.getCode());
            }
        }
        return hotList;
    }

    /**
     * 查询获取二级分类信息
     *
     * @return
     */
    public List<FilterOption> getFilterOptions() {
        //查询所有分类
        List<TreeBook> treeBooks = treeBookService.getTreeBookListByCodeToDB(ConditionConstant.CONDITION_CODE,
                ConditionConstant.INT_1, ConditionConstant.INT_2, null);
        List<FilterOption> filterOptions = new ArrayList<>();
        FilterOption filterOption = null;
        for (TreeBook treeBook : treeBooks) {
            //是二级分类
            if (!ConditionConstant.CONDITION_CODE.equals(treeBook.getPcode())) {
                filterOption = new FilterOption();
                ActionDto actionDto = getActionDto(treeBook.getId());
                InputDto inputDto = getInputDto(treeBook.getId());

                filterOption.setValue(treeBook.getCode());
                filterOption.setLabel(treeBook.getName());
                filterOption.setHint(StringUtils.isEmpty(treeBook.getDescription()) ? null : treeBook.getDescription());
                filterOption.setUnit(StringUtils.isEmpty(treeBook.getExt1()) ? null : treeBook.getExt1());
                filterOption.setStatus(StringUtils.isEmpty(treeBook.getExt5()) ? null : treeBook.getExt5());
                filterOption.setAction(actionDto);
                filterOption.setInput(inputDto);

                filterOptions.add(filterOption);
            }
        }
        return filterOptions;
    }

    /**
     * 根据分类id获取action
     * <p>
     * 如果action_type是5 则直接去option里面查询
     *
     * @param categorieId
     * @return
     */
    public ActionDto getActionDto(Long categorieId) {
        ActionDto actionDto = new ActionDto();
        Action action = actionDao.selectAction(categorieId);
        if (action != null) {
            actionDto.setType(action.getActionType());
            actionDto.setTypeValue(action.getTypeValue());
            if (ConditionConstant.INT_5.equals(actionDto.getType())) {
                List<OptionDto> optionDtos = optionDao.selectOptionDtos(action.getId());
                actionDto.setOptions(optionDtos);
            }
        }
        return actionDto;
    }

    /**
     * 根据分类id获取input
     *
     * @param categorieId
     * @return
     */
    public InputDto getInputDto(Long categorieId) {
        InputDto inputDto = inputDao.selectInput(categorieId);
        return inputDto;
    }

    /**
     * 返回InputValue
     * 在数据字典内下拉数据是4级结构，最少3级结构
     *
     * @return
     */
    public Map<String, Object> getInputValueDto() {
        Map<String, Object> inputValues = new HashMap<>();
        Map parmMap = new HashMap();
        parmMap.put(ConditionConstant.PCODE, ConditionConstant.INPUT_VALUES_CODE);
        List<TreeBook> pTreeBooks = treeBookService.getTreeBooksByCondition(parmMap);
        parmMap.put(ConditionConstant.PCODE, ConditionConstant.INPUT_VALUES_CODE1);
        List<TreeBook> pTreeBooks1 = treeBookService.getTreeBooksByCondition(parmMap);
        pTreeBooks.addAll(pTreeBooks1);
        //树结构
        TreeDto treeDto = null;
        //普通下拉
        List<String> strings = null;
        //inputvalue对象
        InputValueDto inputValueDto = null;
        //二级子参数参数map
        Map twoParamMap = null;
        //三级子参数参数map
        Map threeParamMap = null;
        //下拉树的子集
        List<String> second_level = null;
        //下拉树集合
        List<TreeDto> treeDtos = null;
        for (TreeBook treeBook : pTreeBooks) {
            twoParamMap = new HashMap();
            twoParamMap.put(ConditionConstant.PCODE, treeBook.getCode());
            List<TreeBook> twoTreeBooks = treeBookService.getTreeBooksByCondition(twoParamMap);
            if (!twoTreeBooks.isEmpty()) {
                inputValueDto = new InputValueDto();
                inputValueDto.setName(treeBook.getCode());
                //组装树或普通下拉
                strings = new ArrayList<>();
                treeDtos = new ArrayList<>();
                for (TreeBook twoTreeBook : twoTreeBooks) {
                    threeParamMap = new HashMap();
                    threeParamMap.put(ConditionConstant.PCODE, twoTreeBook.getCode());
                    List<TreeBook> threeTreeBooks = treeBookService.getTreeBooksByCondition(threeParamMap);
                    //如果有第四级
                    if (!threeTreeBooks.isEmpty()) {
                        treeDto = new TreeDto();
                        treeDto.setFirst_level(twoTreeBook.getName());
                        second_level = new ArrayList<>();
                        for (TreeBook threeTreeBook : threeTreeBooks) {
                            second_level.add(threeTreeBook.getName());
                        }
                        treeDto.setSecond_level(second_level);
                        treeDtos.add(treeDto);
                    } else {//如果没有第四级
                        strings.add(twoTreeBook.getName());
                    }
                    if (!strings.isEmpty()) {
                        inputValueDto.setStrings(strings);
                    } else {
                        inputValueDto.setTreeDtos(treeDtos);
                    }
                }
                if (inputValueDto.getTreeDtos() != null && !inputValueDto.getTreeDtos().isEmpty()) {
                    inputValues.put(inputValueDto.getName(), inputValueDto.getTreeDtos());
                } else {
                    inputValues.put(inputValueDto.getName(), inputValueDto.getStrings());
                }

            }
        }
        return inputValues;
    }


}

package net.dgg.bigdata.manager.condition.dao;

import net.dgg.bigdata.common.entity.condition.Input;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/11 13:56
 * @Description:
 */
@Repository
public interface InputDao  {

    @Select("select id,input_type as inputType,placeholder,max,min,max_keyword_length as maxKeywordLength,max_length AS maxLength,treebook_id as treebookId from yk_input where treebook_id = #{treeBookID}")
    List<Input> getInputByTreeBookId(Long treeBookID);
}

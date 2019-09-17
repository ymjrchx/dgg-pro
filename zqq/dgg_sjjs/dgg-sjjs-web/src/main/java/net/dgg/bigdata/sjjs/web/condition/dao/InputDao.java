package net.dgg.bigdata.sjjs.web.condition.dao;

import net.dgg.bigdata.sjjs.web.condition.dto.InputDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/12 11:24
 * @Description:
 */
@Repository
public interface InputDao {

    @Select("select input_type AS 'type',placeholder,max,min,max_keyword_length AS maxKeywordLength ,max_length AS maxLength,options_from as optionsFrom  from yk_input where treebook_id = #{categorieId} limit 1")
    public InputDto selectInput(@Param("categorieId") Long categorieId);
}

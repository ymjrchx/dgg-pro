package net.dgg.dqc.backservice.dao_mybatis;

import net.dgg.dqc.backservice.entity.ImgDealResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <ImgDealResultDao>
 * @despriction 生产主订单Dao
 * @create 2018-10-13 10:19:32+
 **/ 
@Repository
public interface ImgDealResultDao {
    void save(ImgDealResult imgDealResult);

    void update(ImgDealResult imgDealResult);

    ImgDealResult findById(@Param("id") Long id);

    List<ImgDealResult> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}
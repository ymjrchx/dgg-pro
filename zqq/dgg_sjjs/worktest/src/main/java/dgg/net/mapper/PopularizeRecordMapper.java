package dgg.net.mapper;

import dgg.net.entity.PopularizeRecord;

public interface PopularizeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PopularizeRecord record);

    int insertSelective(PopularizeRecord record);

    PopularizeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PopularizeRecord record);

    int updateByPrimaryKey(PopularizeRecord record);
}
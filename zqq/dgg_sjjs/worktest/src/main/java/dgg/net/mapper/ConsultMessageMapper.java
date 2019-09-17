package dgg.net.mapper;

import dgg.net.entity.ConsultMessage;
import dgg.net.entity.ConsultMessageKey;
import dgg.net.entity.ConsultMessageWithBLOBs;

public interface ConsultMessageMapper {
    int deleteByPrimaryKey(ConsultMessageKey key);

    int insert(ConsultMessageWithBLOBs record);

    int insertSelective(ConsultMessageWithBLOBs record);

    ConsultMessageWithBLOBs selectByPrimaryKey(ConsultMessageKey key);

    int updateByPrimaryKeySelective(ConsultMessageWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ConsultMessageWithBLOBs record);

    int updateByPrimaryKey(ConsultMessage record);
}
package net.dgg.dqc.backservice.service;

import net.dgg.dqc.backservice.dao_mybatis.ImportDataRecordDao;
import net.dgg.dqc.backservice.entity.ImportDataRecord;
import net.dgg.dqc.backservice.es.EsClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <ImportRecordService>
 * @despriction 数据到记录Service
 * @create 2018/08/23 11:02
 **/
@Service
public class ImportDataRecordService {


    private String exServer = EsClientUtils.SERVERS;
    private String type = EsClientUtils.TYPE;

    private String index = EsClientUtils.INDEX;


    @Autowired
    private ImportDataRecordDao importDataRecordDao;


    @Transactional
    public void save(ImportDataRecord record) {
        record.setEsServer(this.exServer);
        record.setIndex(this.index);
        record.setType(this.type);
        this.importDataRecordDao.save(record);
    }

    @Transactional
    public void update(ImportDataRecord record) {
        this.importDataRecordDao.update(record);
    }

    @Transactional(readOnly = true)
    public List<ImportDataRecord> query(Map map) {
        return this.importDataRecordDao.query(map);
    }

    public ImportDataRecord findById(Long id) {
        return this.importDataRecordDao.findById(id);
    }


}

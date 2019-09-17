package net.dgg.gspt.dqc.dao.fileUpload;

import net.dgg.gspt.dqc.entity.fileUpload.FileUploadRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <FileUploadRecordDao>
 * @despriction
 * @create 2018-10-10 15:11:11+
 **/
@Repository
public interface FileUploadRecordDao {
    void save(FileUploadRecord fileUploadRecord);

    void update(FileUploadRecord fileUploadRecord);

    FileUploadRecord findById(@Param("id") Long id);

    List<FileUploadRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}
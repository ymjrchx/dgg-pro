package net.dgg.zqq.dao.invoice;

import net.dgg.zqq.entity.invoice.Invoice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <InvoiceDao>
 * @despriction
 * @create 2018-11-09 13:39:53+
 **/
@Repository
public interface InvoiceDao {
    void save(Invoice invoice);

    void update(Invoice invoice);

    Invoice findById(@Param("id") Long id);

    List<Invoice> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}
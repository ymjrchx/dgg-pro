package net.dgg.zqq.services.trademark;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/10/11.
 */
public interface TrademarkService {

    List<Map<String, Object>> getLast12Terms();

    Map<String, Object> queryPage(Map<String, Object> params);

    Map<String, Object> getTradeMark(String id);

    Map<Integer, String> getAllTypes();

    void exportResponse(Map<String, Object> params, HttpServletResponse response);

    void exportPdf(String id, HttpServletResponse response);

    List<Map<String, Object>> queryField(String field, String text, int count);

    Map<Integer, String> getDistinctNumericTypeText(String index, String
            type, Map<String, Object> condition, String field);

    public Map<Integer, List<String>> getDistinctNumericTypeMultiText(String index, String
            type, Map<String, Object> condition, String field);
}

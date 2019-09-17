package net.dgg.bigdata.sjjs.web.component;

import net.dgg.bigdata.sjjs.web.dao.WebConfParamDao;
import net.dgg.bigdata.sjjs.web.entity.WebConfParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 刘阳
 * @ClassName <WebConfService>
 * @despriction web 配置Service
 * @create 2018/08/09 10:00
 **/
@Service
public class WebConfService {
    @Autowired
    private WebConf webConf;

    @Autowired
    private WebConfParamDao webConfParamDao;

    /**
     * 查询所有参数
     *
     * @return
     */
    public List<WebConfParam> queryAll() {
        return webConfParamDao.query(null);
    }


}

package net.dgg.zqq.services.webConf;

import net.dgg.tmd.foundation.platform.session.CommonSession;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.dgg.zqq.component.WebConf;
import net.dgg.zqq.constant.UserIdKeyConstant;
import net.dgg.zqq.dao.webConf.WebConfParamDao;
import net.dgg.zqq.entity.webConf.WebConfParam;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
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
    private SessionManager sessionManager;
    @Autowired
    private UserRecorderDAO userRecorderDAO;

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

    /**
     * 更新
     */
    @Transactional
    public void updateConf(String code, String value) {
        Assert.hasText(code, "参数code不能为空！");
        Assert.hasText(value, "配置值不能为空！");
        List<WebConfParam> webConfParams = this.webConfParamDao.query(new HashMap() {{
            put("code", code);
        }});
        WebConfParam webConfParam = webConfParams.size() > 0 ? webConfParams.get(0) : new WebConfParam();
        webConfParam.setCode(code);
        webConfParam.setValue(value);

        CommonSession session = this.sessionManager.getCurrentSession();
        Assert.notNull(session, "登录超期，请重新登录！");
        UserEntity curUser = this.userRecorderDAO.findUserEntityByUserId(session.getValue(UserIdKeyConstant.KEY, Long.class));
        Assert.notNull(curUser, "未查询到当前用户！");

        if (webConfParam.getId() == null) {
            webConfParam.setCreateUser(curUser);
        }
        webConfParam.setName(this.webConf.getFieldExplain(code));
        webConfParam.setUpdaterUser(curUser);
        if (webConfParam.getId() == null) {
            webConfParam.setId(KeyWorker.nextId());
            this.webConfParamDao.save(webConfParam);
        } else {
            this.webConfParamDao.update(webConfParam);
        }

        // 更新bean 和 redis
        try {
            this.webConf.setSelfAndRedis(code, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException("此参数未定义到WebConf类中！");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("redis缓存更新失败");
        }

    }


}

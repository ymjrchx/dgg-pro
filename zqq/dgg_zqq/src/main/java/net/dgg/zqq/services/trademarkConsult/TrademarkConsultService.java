package net.dgg.zqq.services.trademarkConsult;


import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.dgg.zqq.constant.UserIdKeyConstant;
import net.dgg.zqq.dao.trademarkConsult.TrademarkConsultDao;
import net.dgg.zqq.dao.trademarkConsult.TrademarkConsultExtDao;
import net.dgg.zqq.entity.trademarkConsult.TrademarkConsult;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TrademarkConsultService {

    @Autowired
    private TrademarkConsultDao trademarkConsultDao;
    @Autowired
    private TrademarkConsultExtDao trademarkConsultExtDao;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserRecorderDAO userRecorderDAO;

    public void save(String trademarkeName, String trademarkerType, String userName, String phone, String email, String intentionalPrice, String requirementDesc, String applicationNum) {
        //Assert.hasText(trademarkeName, "trademarkeName不能为空！");
        Assert.hasText(trademarkerType, "trademarkerType不能为空！");
        Assert.hasText(userName, "userName不能为空！");
        Assert.hasText(phone, "phone不能为空！");
        Assert.hasText(intentionalPrice, "intentionalPrice不能为空！");
        Assert.hasText(applicationNum, "applicationNum不能为空！");

        TrademarkConsult trademarkConsult = new TrademarkConsult();
        trademarkConsult.setId(KeyWorker.nextId());
        trademarkConsult.setTrademarkerName(trademarkeName);
        trademarkConsult.setTrademarkerType(trademarkerType);
        trademarkConsult.setUserName(userName);
        trademarkConsult.setPhone(phone);
        trademarkConsult.setEmail(email);
        trademarkConsult.setIntentionalPrice(intentionalPrice);
        trademarkConsult.setRequirementDesc(requirementDesc);
        trademarkConsult.setApplicationNum(applicationNum);
        trademarkConsult.setFlag(1);
        trademarkConsult.setCreateTime(new Date());

        trademarkConsultDao.save(trademarkConsult);
    }

    public List<Map> pageQuery(Map query) {

        Integer count = this.trademarkConsultExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.trademarkConsultExtDao.queryMap(query);
        } else {
            return Collections.emptyList();
        }
    }

    public void delete(Long id, Integer flag) {

        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");
        Assert.isTrue(flag != null && (flag == 0 || flag == 1), "flag传参错误");

        //根据ID查询
        TrademarkConsult trademark = trademarkConsultDao.findById(id);
        Assert.isTrue(trademark != null, "传递参数为空");
        if (flag == 1) {
            Assert.isTrue(trademark.getFlag() != null && trademark.getFlag() == 1, "该条记录已经是已联系");
            trademark.setFlag(0);
        }

        if (flag == 0) {
            Assert.isTrue(trademark.getFlag() != null && trademark.getFlag() == 0, "该条记录已经是未联系");
            trademark.setFlag(1);
        }

        trademark.setUpdaterUser(userEntity);
        trademarkConsultDao.update(trademark);

    }
}

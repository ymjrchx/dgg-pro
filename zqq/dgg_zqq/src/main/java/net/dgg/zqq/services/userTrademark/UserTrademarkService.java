package net.dgg.zqq.services.userTrademark;

import net.dgg.zqq.controller.search.BrandManagerController;
import net.dgg.zqq.dao.userTrademark.UserCompanyDao;
import net.dgg.zqq.dao.userTrademark.UserCompanyExtDao;
import net.dgg.zqq.dao.userTrademark.UserTrademarkDao;
import net.dgg.zqq.dao.userTrademark.UserTrademarkExtDao;
import net.dgg.zqq.entity.userTrademark.UserCompany;
import net.dgg.zqq.entity.userTrademark.UserTrademark;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserTrademarkService {

    @Autowired
    private BrandManagerController brandManagerController;

    @Autowired
    private UserTrademarkDao userTrademarkDao;

    @Autowired
    private UserTrademarkExtDao userTrademarkExtDao;

    @Autowired
    private UserCompanyDao userCompanyDao;

    @Autowired
    private UserCompanyExtDao userCompanyExtDao;

    public List findTrademarkByName(String name,String userId) {
        //验证用户是否登录
        Assert.hasText(userId, "用户ID不能为空！");
        //Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
        Assert.hasText(name,"企业名称不能为空");
        List<String> list = brandManagerController.searchFive(name);
        return list;
    }

    public void importTrademark(List<String> applicantCns, String userId) {
        //验证用户是否登录
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
        Assert.isTrue(applicantCns.size() > 0 && applicantCns.size() < 4, "导入企业个数只能在3条及3条以内");

        //遍历
        for (String applicantCn : applicantCns) {
            //导入企业
            UserCompany userCompany = userCompanyExtDao.findByMap(new HashMap() {{
                put("userId", userId);
                put("companyName", applicantCn);
                put("flag", 1);  //未删除
            }});
            if(userCompany==null){
                //未导入企业
                UserCompany userCompany2 = new UserCompany();
                userCompany2.setId(KeyWorker.nextId());
                userCompany2.setUserId(userId);
                userCompany2.setFlag(1);
                String companyId = KeyWorker.nextId()+"";
                userCompany2.setCompanyId(companyId);
                userCompany2.setCompanyName(applicantCn);
                userCompany2.setCreateTime(new Date());
                userCompany2.setCompanyId(companyId);
                userCompanyDao.save(userCompany2);

                //导入商标
                importTra(userId, applicantCn, companyId);

            }else{
                //已导入企业
                importTra(userId, applicantCn, userCompany.getCompanyId());
            }
        }

    }

    private void importTra(String userId, String applicantCn, String companyId) {
        //判断es中是否能查询到
        List<Map> trademarks = brandManagerController.getListMapBrandManager(1, 10, applicantCn);
        Assert.isTrue(trademarks.size()>0,"没有查询到"+applicantCn+"下的商标");

        /*if(trademarks.size()<0){
            return;
        }*/
        for (Map trademark : trademarks) {

            //判断是否已经导入过商标
            UserTrademark userTrademarkSys = userTrademarkExtDao.findByMap(new HashMap() {{
                put("userId", userId);
                put("registerNo", (String) trademark.get("regNo"));
                put("flag", 1);
            }});
            if(userTrademarkSys!=null){
                continue;
            }

            //没有导入
            UserTrademark userTrademark = new UserTrademark();
            userTrademark.setId(KeyWorker.nextId());
            userTrademark.setUserId(userId);
            userTrademark.setRegisterNo((String) trademark.get("regNo"));
            userTrademark.setTrademarkFile((String) trademark.get("imageUrl"));
            userTrademark.setName((String) trademark.get("name"));
            userTrademark.setClassLevel1Code((String) trademark.get("intCls"));

            //处理日期
            String string = (String) trademark.get("appDate");
            if(string!=null && string !=""){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = dateFormat.parse(string);
                    userTrademark.setApplyDate(date);
                }  catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            userTrademark.setCreateTime(new Date());
            userTrademark.setApplicantId(companyId);
            userTrademark.setApplicant((String) trademark.get("applicantCn"));
            userTrademark.setStatus((String) trademark.get("status"));
            userTrademark.setFlag(1);  //默认正常

            userTrademarkDao.save(userTrademark);

        }
    }


    /*
     *  查询用户已导入的企业
     */
    public List<UserCompany> findCompanyByUserId(String userId,Integer flag,Integer pageSize, Integer pageNum) {
        //验证用户是否登录
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
        Assert.isTrue(flag!=null && (flag==0 || flag==1),"flag传递错误");

        if(pageSize==null || pageSize<0){
            pageSize=10; //如果不设置 默认查询10条
        }
        if(pageNum==null || pageNum<0){
            pageNum=1;  //如果不设置页码 默认查询第一页
        }
        Integer start = (pageNum-1)*pageSize;
        Integer limit = pageSize;
        List<UserCompany> list = userCompanyExtDao.selectByFlag(new HashMap() {{
            put("start", start);
            put("limit", limit);
            put("userId", userId);
            put("flag", flag);
        }});
        return list;
    }


    /*
     *  删除用户下的企业（删除该企业下的商标）
     */
    public void delCompanyByUserId(String userId, String ids) {
        //验证用户是否登录
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
        Assert.hasText(ids,"ids不能为空");
        String[] strIds = ids.split(",");
        Assert.isTrue(strIds.length>0,"ids传参错误");
        Long[] longIds = new Long[strIds.length];
        for (int i = 0; i < strIds.length; i++) {
            longIds[i] = Long.parseLong(strIds[i]);
        }

        for (Long id : longIds) {
            //查询企业
            UserCompany userCompany = userCompanyExtDao.findByMap(new HashMap() {{
                put("userId", userId);
                put("id", id);
                put("flag", 1);
            }});
            Assert.notNull(userCompany,"传参错误");

            //删除用户企业下的商标
            List<UserTrademark> userTrademarkList = userTrademarkDao.query(new HashMap() {{
                put("userId", userId);
                put("applicantId", userCompany.getCompanyId());
                put("flag", 1);
            }});

            if(userTrademarkList.size()>0){
                //企业下有商标  删除企业下的商标
                for (UserTrademark userTrademark : userTrademarkList) {
                    userTrademark.setFlag(0);
                    userTrademark.setUpdateTime(new Date());
                    userTrademarkDao.update(userTrademark);
                }
            }

            //删除企业
            userCompany.setFlag(0);
            userCompany.setUpdateTime(new Date());
            userCompanyDao.update(userCompany);

        }


    }

    /*
     *  查询用户下满足条件的商标
     */
    public Map<String,Object> findByNameAndUserId(String name, String userId,Integer pageSize,Integer pageNum) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");

        if(pageSize==null || pageSize<0){
            pageSize=10;
        }
        if(pageNum==null || pageNum<0){
            pageNum=1;
        }
        Integer start = (pageNum-1)*pageSize;

        //结果封装map
        Map<String,Object> mapResult = new HashMap();

        //条件查询map
        Map map = new HashMap();
        if(name!=null){
            map.put("name",name);
        }
        map.put("flag",1);
        map.put("userId",userId);
        map.put("start",start);
        map.put("limit",pageSize);
        List<UserTrademark> list = userTrademarkExtDao.query(map);

        /*//循环拼接图片地址
        for (UserTrademark userTrademark : list) {
            if(!StringUtils.isEmpty(userTrademark.getTrademarkFile())){
                userTrademark.setTrademarkFile(ClientAipOcr.getFileUrlPrefix().concat(userTrademark.getTrademarkFile()));
            }
        }*/

        mapResult.put("list",list);

        //数量条件查询map
        Map countMap = new HashMap();
        if(name!=null){
            countMap.put("name",name);
        }
        countMap.put("userId",userId);
        countMap.put("flag",1);
        Integer count = userTrademarkExtDao.count(countMap);
        mapResult.put("count",count);

        return mapResult;

    }


    /*
     * 删除用户下的商标
     */
    public void deleteArr(Long[] ids, String userId) {
        //验证用户是否登录
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
        Assert.isTrue(ids.length > 0, "商标ID数组shu不能少于1");

        for (Long id : ids) {
            //查询是否存在商标
            UserTrademark userTrademark = userTrademarkExtDao.findByMap(new HashMap() {{
                put("flag", 1);
                put("userId", userId);
                put("id", id);
            }});

            Assert.notNull(userTrademark,"传参错误");

            //删除商标（修改flag）
            userTrademark.setFlag(0);
            userTrademark.setUpdateTime(new Date());
            userTrademarkDao.update(userTrademark);

            //判断该商标对应的公司是否还有商标
            String applicantId = userTrademark.getApplicantId();
            Assert.isTrue(applicantId != null && applicantId != "", "该商标没有所属公司");
            List<UserTrademark> list = userTrademarkDao.query(new HashMap() {{
                put("flag", 1);
                put("applicantId", applicantId);
                put("userId", userId);
            }});

            if (list == null || list.size() < 1) {
                List<UserCompany> companyList = userCompanyDao.query(new HashMap() {{
                    put("flag", 1);
                    put("companyId", applicantId);
                }});
                Assert.isTrue(companyList != null && companyList.size() == 1, "公司已经不存在 商标还存在");
                UserCompany userCompany = companyList.get(0);

                //删除企业
                userCompany.setFlag(0);
                userCompany.setUpdateTime(new Date());
                userCompanyDao.update(userCompany);

            }



        }
    }


    /*
     *  导出excel
     */
    public List<UserTrademark> findByAll(String userId) {
        //验证用户是否登录
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");

        List<UserTrademark> list = userTrademarkExtDao.query(new HashMap() {{
            put("flag", 1);
            put("userId", userId);
        }});
        return list;
    }



}

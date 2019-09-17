package net.dgg.zqq.services.order;

import net.dgg.zqq.constant.ApplocantTypeConstant;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.dao.order.ApplicantTemplateDao;
import net.dgg.zqq.dao.order.ApplicantTemplateExtDao;
import net.dgg.zqq.entity.order.ApplicantTemplate;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.dgg.zqq.services.fast_dfs.FastDfsService;
import net.dgg.zqq.services.fileUpload.FileUploadService;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ApplicantTemplateService
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/11/14 9:53
 */

@Service
@Transactional
public class ApplicantTemplateService {

    @Autowired
    private ApplicantTemplateDao applicantTemplateDao;
    @Autowired
    private ApplicantTemplateExtDao applicantTemplateExtDao;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FastDfsService fastDfsService;

    /**
     * 增加申请模板
     *
     * @param applicantTemplate
     */
    public void save(ApplicantTemplate applicantTemplate) {
        //验证用户是否登录
        Assert.hasText(applicantTemplate.getCreaterId(), "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(applicantTemplate.getCreaterId()), "登陆超期，请重新登录！");

        Assert.hasText(applicantTemplate.getApplicantType(), "申请人类型不能为空！");
        Assert.hasText(applicantTemplate.getTemplateName(), "模板名称不能为空！");

        Assert.hasText(applicantTemplate.getApplicantName(), "申请主体不能为空！");
        Assert.hasText(applicantTemplate.getBusinessLicenceAddress(), "申请地址不能为空！");//营业执照所在地区
        Assert.hasText(applicantTemplate.getBusinessLicenceFile(), "营业执照不能为空！");
        Assert.hasText(applicantTemplate.getBusinessLicenceNo(), "营业执照号不能为空！");

        Assert.hasText(applicantTemplate.getContactName(), "联系人不能为空！");
        Assert.hasText(applicantTemplate.getContactPhone(), "联系人电话不能为空！");
        Assert.hasText(applicantTemplate.getContactEmail(), "联系人邮箱不能为空！");
        Assert.hasText(applicantTemplate.getPostalcode(), "邮政编码不能为空！ ");

        if (ApplocantTypeConstant.PERSON.equals(applicantTemplate.getApplicantType())) {
            //个体工商户
            Assert.hasText(applicantTemplate.getApplicantCardNo(), "申请人身份证号不能为空");
            Assert.hasText(applicantTemplate.getApplicantCardFile(), "身份证复印件不能为空！");
            Assert.hasText(applicantTemplate.getApplicantUserName(), "申请人不能为空！");
            Assert.hasText(applicantTemplate.getApplicantCardAddress(), "申请人地址不能为空！");
        }

        if (applicantTemplate.getId() == null) {
            //保存
            applicantTemplate.setId(KeyWorker.nextId());
            applicantTemplate.setStatus(StatusConstant.ENABLE);
            applicantTemplate.setFlag(1);
            applicantTemplate.setCreateTime(new Date());
            Boolean falg = hasDefault(applicantTemplate.getCreaterId());
            applicantTemplate.setDefaultSign(falg ? 0 : 1);
            applicantTemplateDao.save(applicantTemplate);

        } else {
            //修改
            ApplicantTemplate sys = applicantTemplateDao.findById(applicantTemplate.getId());
            Assert.notNull(sys, "ID传递错误");
            sys.setUpdaterId(applicantTemplate.getCreaterId());
            sys.setApplicantType(applicantTemplate.getApplicantType());
            sys.setTemplateName(applicantTemplate.getTemplateName());

            sys.setApplicantName(applicantTemplate.getApplicantName());
            sys.setBusinessLicenceAddress(applicantTemplate.getBusinessLicenceAddress());
            sys.setBusinessLicenceFile(applicantTemplate.getBusinessLicenceFile());
            sys.setBusinessLicenceNo(applicantTemplate.getBusinessLicenceNo());

            sys.setContactName(applicantTemplate.getContactName());
            sys.setContactPhone(applicantTemplate.getContactPhone());
            sys.setContactEmail(applicantTemplate.getContactEmail());
            sys.setPostalcode(applicantTemplate.getPostalcode());
            sys.setUpdateTime(new Date());

            if (ApplocantTypeConstant.PERSON.equals(applicantTemplate.getApplicantType())) {
                //个体工商户
                sys.setApplicantCardNo(applicantTemplate.getApplicantCardNo());
                sys.setApplicantCardFile(applicantTemplate.getApplicantCardFile());
                sys.setApplicantUserName(applicantTemplate.getApplicantUserName());
                sys.setApplicantCardAddress(applicantTemplate.getApplicantCardAddress());
            }

            applicantTemplateDao.update(sys);
        }

    }

    /**
     * 查看是否存在默认
     *
     * @param createrId
     * @return
     */
    private Boolean hasDefault(String createrId) {
        //设置默认
        Integer count = applicantTemplateDao.count(new HashMap() {{
            put("flag", 1);
            put("status", StatusConstant.ENABLE);
            put("createrId", createrId);
            put("defaultSign", 1);
        }});

        return (count == null || count < 1) ? false : true;
    }


    /**
     * 根据id和用户id查找模板
     *
     * @param id
     * @param createrId
     * @return
     */
    public ApplicantTemplate findById(Long id, String createrId) {
        //验证用户是否登录
        Assert.hasText(createrId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(createrId), "登陆超期，请重新登录！");

        ApplicantTemplate applicantTemplateDaoById = applicantTemplateDao.findById(id);
        Assert.notNull(applicantTemplateDaoById, "ID传递错误");
        Assert.isTrue(createrId.equals(applicantTemplateDaoById.getCreaterId()), "传参错误");
        return applicantTemplateDaoById;
    }

    /**
     * 分页查找
     *
     * @param createrId
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Map<String, Object> queryByPage(String createrId, Integer pageSize, Integer pageNum) {
        //验证用户是否登录
        Assert.hasText(createrId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(createrId), "登陆超期，请重新登录！");

        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }
        if (pageNum == null || pageNum < 0) {
            pageNum = 1;
        }
        Integer start = (pageNum - 1) * pageSize;
        Integer limit = pageSize;
        Map map = new HashMap();
        Integer count = applicantTemplateDao.count(new HashMap() {{
            put("flag", 1);
            put("status", StatusConstant.ENABLE);
            put("createrId", createrId);
        }});
        map.put("count", count);

        //倒序查找
        List<ApplicantTemplate> list = applicantTemplateExtDao.query(new HashMap() {{
            put("flag", 1);
            put("status", StatusConstant.ENABLE);
            put("start", start);
            put("limit", limit);
            put("createrId", createrId);
        }});
        map.put("list", list);
        map.put("fsHost", fileUploadService.getHost());

        return map;


    }

    /**
     * 删除申请人模板
     *
     * @param createrId
     * @param id
     */
    public void del(String createrId, Long id) {
        //验证数据
        Assert.hasText(createrId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(createrId), "登陆超期，请重新登录！");
        ApplicantTemplate applicantTemplate = applicantTemplateDao.findById(id);
        Assert.isTrue(applicantTemplate != null && createrId.equals(applicantTemplate.getCreaterId()), "传参错误");

        //删除文件服务器中的老文件
        /*Boolean flag = fastDfsService.deleteFile(applicantTemplate.getBusinessLicenceFile());
        Assert.isTrue(flag, "营业执照文件路径错误或被篡改");
        applicantTemplate.setBusinessLicenceFile(null);
        if (ApplocantTypeConstant.PERSON.equals(applicantTemplate.getApplicantType())) {
            //个人
            Boolean flag2 = fastDfsService.deleteFile(applicantTemplate.getApplicantCardFile());
            Assert.isTrue(flag2, "身份证文件路径错误或被篡改");
            applicantTemplate.setApplicantCardFile(null);
        }*/

        applicantTemplate.setFlag(0);
        applicantTemplateDao.update(applicantTemplate);
        //是否需要重新设置默认
        Boolean falg = hasDefault(createrId);
        if (!falg) {
            List<ApplicantTemplate> list = applicantTemplateDao.query(new HashMap() {{
                put("flag", 1);
                put("status", StatusConstant.ENABLE);
                put("createrId", createrId);
                put("limit", 1);
            }});
            if (list != null && list.size() > 0) {
                ApplicantTemplate app = list.get(0);
                app.setDefaultSign(1);
                applicantTemplateDao.update(app);
            }
        }


    }

    /**
     * 设置默认
     *
     * @param createrId
     * @param id
     */
    public void makeDefault(String createrId, Long id) {
        Assert.hasText(createrId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(createrId), "登陆超期，请重新登录！");
        ApplicantTemplate applicantTemplate = applicantTemplateDao.findById(id);
        Assert.isTrue(applicantTemplate != null && createrId.equals(applicantTemplate.getCreaterId()), "传参错误");
        Assert.isTrue(applicantTemplate.getDefaultSign() == 0, "该申请模板已经是默认");

        //将系统默认的模板设置为不默认
        List<ApplicantTemplate> list = applicantTemplateDao.query(new HashMap() {{
            put("flag", 1);
            put("status", StatusConstant.ENABLE);
            put("createrId", createrId);
            put("defaultSign", 1);
        }});
        if (list != null && list.size() == 1) {
            ApplicantTemplate applicantTemplate1 = list.get(0);
            applicantTemplate1.setDefaultSign(0);
            applicantTemplateDao.update(applicantTemplate1);
        }

        //设置默认
        applicantTemplate.setDefaultSign(1);
        applicantTemplateDao.update(applicantTemplate);
    }

    /**
     * 根据用户id查找不同类型的模板
     *
     * @param createrId
     * @return
     */
    public Map find(String createrId) {
        Assert.hasText(createrId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(createrId), "登陆超期，请重新登录！");

        Map map = new HashMap();
        String[] applicantTypes = new String[]{"applicant_type1", "applicant_type2"};
        for (String applicantType : applicantTypes) {
            List<ApplicantTemplate> list = applicantTemplateExtDao.query(new HashMap() {{
                put("flag", 1);
                put("status", StatusConstant.ENABLE);
                put("createrId", createrId);
                put("applicantType", applicantType);
            }});
            map.put(applicantType, list);
        }
        map.put("fsHost", fileUploadService.getHost());
        return map;
    }

    public ApplicantTemplate findInfoById(Long id) {
        return this.applicantTemplateDao.findById(id);
    }


}
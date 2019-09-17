package net.dgg.gspt.dqc.services.template;


import net.dgg.gspt.dqc.constant.StatusConstant;
import net.dgg.gspt.dqc.constant.TemplateTypeConstant;
import net.dgg.gspt.dqc.constant.UserIdKeyConstant;
import net.dgg.gspt.dqc.dao.template.TemplateDao;
import net.dgg.gspt.dqc.dao.template.TemplateExtDao;
import net.dgg.gspt.dqc.entity.template.Template;
import net.dgg.gspt.dqc.services.fast_dfs.FastDfsService;
import net.dgg.gspt.dqc.services.fileUpload.FileUploadService;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Transactional
public class TemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private TemplateExtDao templateExtDao;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private UserRecorderDAO userRecorderDAO;

    @Autowired
    private FastDfsService fastDfsService;

    @Autowired
    private FileUploadService fileUploadService;


    public List<Map> pageQuery(Map query) {
        query.put("flag", 1);
        Integer count = this.templateExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.templateExtDao.queryMap(query);
        } else {
            return Collections.emptyList();
        }
    }

    public Template findById(Long id) {
        return templateDao.findById(id);

    }

    @Transactional
    public void save(String templateType, String templateName, String status, String url, String fileName) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.notNull(fileName, "文件名称不能为空");
        Assert.notNull(templateType, "模板类型不能为空");
        Assert.notNull(templateName, "模板名称不能为空");
        Assert.notNull(status, "状态不能为空");
        Assert.notNull(url, "文件上传失败");
        Assert.notNull(fileName, "上传文件不能为空");

        Template template = new Template();
        template.setId(KeyWorker.nextId());
        template.setTemplateType(templateType);
        template.setTemplateTypeName(null);
        template.setTemplateName(templateName);
        template.setFileName(fileName);
        template.setFileUrl(url);
        template.setStatus(status);
        template.setFlag(1);
        template.setDownload(0L);
        template.setCreateUser(userEntity);
        templateDao.save(template);

    }

    @Transactional
    public void delete(Long id) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.notNull(id, "id不能为空！");
        Template template = templateDao.findById(id);
        Assert.isTrue(template.getFlag() == 1, "不能重复删除");
        Assert.notNull(template, "未查询到数据");

        //删除文件服务器上的文件
        if (template.getFileUrl() != null && template.getFileUrl() != "") {
            Boolean flag = fastDfsService.deleteFile(template.getFileUrl());
            Assert.isTrue(flag == true, "文件路径错误或被篡改");
        }

        template.setFlag(0);
        template.setUpdaterUser(userEntity);
        template.setFileUrl(null);
        template.setUpdaterUser(userEntity);
        templateDao.update(template);


    }

    public List<Map<String, Object>> findByMap(String userId) {
        //Assert.hasText(userId, "用户ID不能为空！");
        //Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");

        List<Map<String, Object>> resultList = new ArrayList<>();
        String[] arr = new String[]{TemplateTypeConstant.TYPE01, TemplateTypeConstant.TYPE02, TemplateTypeConstant.TYPE03, TemplateTypeConstant.TYPE04, TemplateTypeConstant.TYPE05, TemplateTypeConstant.TYPE06};
        for (String templateType : arr) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("templateType", templateType);

            Map map = new HashMap();
            map.put("flag", 1);
            map.put("status", StatusConstant.ENABLE);
            map.put("templateType", templateType);
            map.put("limit", 1);
            List<Template> list = templateDao.query(map);
            String fileUrl = null;
            if (list != null && list.size() > 0 && !StringUtils.isEmpty(list.get(0).getFileUrl())) {
                fileUrl = fileUploadService.getHost().concat(list.get(0).getFileUrl());
            }
            resultMap.put("fileUrl", fileUrl);
            resultList.add(resultMap);

        }
        return resultList;
    }

    public List<Map<String, Object>> findByTemplateTypes(String templateTypes, Integer limit) {

        Assert.hasText(templateTypes, "templateTypes不能为空！");
        String[] templateArr = templateTypes.split(",");
        Assert.isTrue(templateArr.length > 0, "传递参数错误");
        //结果集合
        List<Map<String, Object>> list = new ArrayList<>();
        for (String templateType : templateArr) {
            //System.err.println(templateType);
            Map<String, Object> map = new HashMap<>();
            map.put("templateType", templateType);
            List<Template> listResult = templateDao.query(new HashMap() {{
                put("flag", 1);
                put("status", StatusConstant.ENABLE);
                put("templateType", templateType);
                put("limit", limit);
            }});

            if (listResult != null && listResult.size() > 0) {
                for (Template template : listResult) {
                    if (template != null && !StringUtils.isEmpty(template.getFileUrl())) {
                        template.setFileUrl(fileUploadService.getHost().concat(template.getFileUrl()));
                    }

                }
            }

            map.put("list", listResult);
            list.add(map);
        }
        return list;
    }


    @Transactional
    public void updateTemplate(Template template) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.isTrue(template.getId() != null, "模板Id不能为空！");
        Assert.hasText(template.getTemplateType(), "templateType不能为空！");
        Assert.hasText(template.getTemplateName(), "templateName不能为空!");
        Assert.hasText(template.getStatus(), "status不能为空!");


        Template templateOld = templateDao.findById(template.getId());
        Assert.notNull(templateOld, "未查询到数据！");

        templateOld.setTemplateType(template.getTemplateType());
        templateOld.setTemplateName(template.getTemplateName());
        templateOld.setStatus(template.getStatus());
        templateOld.setUpdaterUser(userEntity);

        templateDao.update(templateOld);

    }

    @Transactional
    public Long addDownload(Long id) {
        Assert.notNull(id, "id不能为空！");
        Template template = this.templateDao.findById(id);
        Assert.notNull(template, "未查询到模版！");
        template.setDownload(template.getDownload() + 1L);
        this.templateDao.update(template);
        return template.getDownload();
    }

}

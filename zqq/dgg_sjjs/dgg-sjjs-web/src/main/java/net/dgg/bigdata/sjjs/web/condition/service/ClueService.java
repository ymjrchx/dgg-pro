package net.dgg.bigdata.sjjs.web.condition.service;

import com.alibaba.fastjson.JSON;
import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.constant.ConditionEnum;
import net.dgg.bigdata.sjjs.web.condition.dto.ClueDto;
import net.dgg.bigdata.sjjs.web.condition.dto.MqConsumerDto;
import net.dgg.bigdata.sjjs.web.constant.CluesStatus;
import net.dgg.bigdata.sjjs.web.entity.SysUser;
import net.dgg.bigdata.sjjs.web.entity.dto.search.clues.YkClues;
import net.dgg.bigdata.sjjs.web.entity.dto.search.clues.YkCluesRecord;
import net.dgg.bigdata.sjjs.web.service.SysUserService;
import net.dgg.bigdata.sjjs.web.service.search.CluesOptService;
import net.dgg.bigdata.sjjs.web.util.SysUserUtils;
import net.dgg.core.utils.exception.DggBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/21 10:38
 * @Description:
 */
@Service
public class ClueService {

    @Autowired
    private CluesOptService cluesOptService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ClueMqProvider clueMqProvider;

    /**
     * 转线索组装 传对象
     *
     * @param cluesJson
     */
    public void doClue(String cluesJson, HttpServletRequest request) throws Exception {

        //替换掉其中电话参数
        cluesJson = cluesJson.replace(ConditionConstant.COMPANYTEL, ConditionConstant.CONTACTNO);

        List<ClueDto> clueDtos = JSON.parseArray(cluesJson, ClueDto.class);
        if (clueDtos == null || clueDtos.isEmpty()) {
            throw new DggBaseException("不允许转0条线索");
        }

        SysUser user = SysUserUtils.getUser(request);
        //验证工号
        if (user == null) {
            throw new DggBaseException("用户未登陆");
        }

        String buyUser = user.getIbossCode();
        if (StringUtils.isEmpty(buyUser)) {
            throw new DggBaseException("用户未绑定工号");
        }
        List<ClueDto> clueDtoList = new ArrayList<>();
        YkCluesRecord ykCluesRecord = null;
        for (ClueDto clue : clueDtos) {
            //信息不全
            if (StringUtils.isEmpty(clue.getCompanyId()) || StringUtils.isEmpty(clue.getCompanyName())) {
                throw new DggBaseException("信息不全，不能转线索");
            }
            //处理电话
            String phoneNo = clue.getContactNo();
            if (StringUtils.isEmpty(phoneNo) || !phoneNo.matches(".*\\d+.*")) {
                throw new DggBaseException(clue.getCompanyName() + " 电话号码为空或错误");
            } else {
                Matcher matcher = Pattern.compile("[^\\,]{7,11}").matcher(phoneNo);
                boolean flag = false;
                if (matcher.find()) {
                    phoneNo = matcher.group();
                    flag = true;
                }
                if (!flag) {
                    throw new DggBaseException(clue.getCompanyName() + " 电话号码为空或错误");
                }
                String processChainObject = phoneNo.replaceAll("[^\\d]+", "");
                if (processChainObject.length() > 11) {
                    processChainObject = processChainObject.substring(processChainObject.length() - 11);
                }
                clue.setContactNo(processChainObject);
            }

            //调es记录更新历史记录
            LocalDateTime localDateTime = LocalDateTime.now();
            String date = localDateTime.toString();
            date = date.substring(0, date.lastIndexOf("."));
            ykCluesRecord = new YkCluesRecord();
            ykCluesRecord.setComId(clue.getCompanyId());
            ykCluesRecord.setUserId(user.getUserId());
            ykCluesRecord.setComName(clue.getCompanyName());
            ykCluesRecord.setStatus(ConditionEnum.NO_CONFIRM.getValue());
            ykCluesRecord.setToWhere(CluesStatus.CLUES_GET);
            ykCluesRecord.setStatusUpdateTime(date);
            ykCluesRecord.setNum(buyUser);
            ykCluesRecord.setChargePerson(user.getLegenPerson());
            ykCluesRecord.setChargeDept(user.getLegenDept());
            cluesOptService.addRecordDocument(ykCluesRecord);

            //调取线索转换更新最新的状态
            YkClues ykClues = new YkClues();
            ykClues.setComId(clue.getCompanyId());
            ykClues.setComName(clue.getCompanyName());
            ykClues.setUserId(user.getUserId());
            ykClues.setStatus(ConditionEnum.NO_CONFIRM.getValue());
            ykClues.setUpdateTime(date);
            ykClues.setNum(buyUser);
            ykClues.setLegenPerson(user.getLegenPerson());
            ykClues.setLegenDept(user.getLegenDept());
            //调取线索转换service
            cluesOptService.addCluesConverDocument(clue.getCompanyId(), ykClues);

            //设置客户类型，暂时定死为公司
            clue.setCustomerType(ConditionEnum.CUSTOMER_COMPANY.getKey());
            //为每条线索设定购买者
            clue.setBuyUser(buyUser);
            //设置线索公司简介大小 iboss 大小为 255 ，255/2=126
            clue.setCompanyBrief(StringUtils.isEmpty(clue.getCompanyBrief()) ? null : clue.getCompanyBrief().length() < 126 ? clue.getCompanyBrief() : clue.getCompanyBrief().substring(0, 126));
            clueDtoList.add(clue);
        }

        //发送mq消息的方式 转线索到资源中心
        clueMqProvider.mqProvider(clueDtoList);
    }

    /**
     * 转线索组装 传id
     *
     * @param clueIds
     */
    public void doClue(String[] clueIds, HttpServletRequest request) throws Exception {

        List<Map> clues = cluesOptService.getClues(clueIds);
        List<Map> newClues = new ArrayList<>();
        Map clueMap = null;
        for (Map map : clues) {
            clueMap = new HashMap();
            if (map.containsKey(ConditionConstant.COMPANYID) && map.containsKey(ConditionConstant.COMMERCIAL)) {
                clueMap.put(ConditionConstant.COMPANYID, map.get(ConditionConstant.COMPANYID));
                clueMap.putAll((HashMap) map.get(ConditionConstant.COMMERCIAL));
                newClues.add(clueMap);
            } else {
                clueMap = null;
            }
        }
        String cluesJson = JSON.toJSONString(newClues);
        doClue(cluesJson, request);
    }

    /**
     * 组装对象并调取es
     *
     * @param mqConsumerDto
     * @throws Exception
     */
    public void clueToEs(MqConsumerDto mqConsumerDto) throws Exception {

        String changeSatus = ConditionEnum.forKeyToValue(mqConsumerDto.getStatus());
        SysUser user = sysUserService.findByIbossCode(mqConsumerDto.getBuyUser());
        if (user == null) {
            throw new DggBaseException("用户工号未获取到用户");
        }
        //组装对象
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = localDateTime.toString();
        date = date.substring(0, date.lastIndexOf("."));

        //调取线索转换
        YkClues ykClues = new YkClues();
        ykClues.setComId(mqConsumerDto.getCompanyId());
        ykClues.setComName(mqConsumerDto.getCompanyName());
        ykClues.setUserId(user.getUserId());
        ykClues.setNum(mqConsumerDto.getBuyUser());
        ykClues.setStatus(changeSatus);
        ykClues.setUpdateTime(date);
        ykClues.setLegenPerson(user.getLegenPerson());
        ykClues.setLegenDept(user.getLegenDept());
        //调取线索转换service
        boolean addClue = cluesOptService.addCluesConverDocument(mqConsumerDto.getCompanyId(), ykClues);

        //调取线索记录
        YkCluesRecord ykCluesRecord = new YkCluesRecord();
        ykCluesRecord.setComId(mqConsumerDto.getCompanyId());
        ykCluesRecord.setUserId(user.getUserId());
        ykCluesRecord.setNum(mqConsumerDto.getBuyUser());
        ykCluesRecord.setStatus(changeSatus);
        ykCluesRecord.setToWhere(CluesStatus.CLUES_PROCESS);
        ykCluesRecord.setStatusUpdateTime(date);
        ykCluesRecord.setChargePerson(user.getLegenPerson());
        ykCluesRecord.setChargeDept(user.getLegenDept());
        ykCluesRecord.setComName(mqConsumerDto.getCompanyName());
        ykCluesRecord.setReason(mqConsumerDto.getReason());
        //调取线索记录service
        boolean addRecord = cluesOptService.addRecordDocument(ykCluesRecord);
        if (!addClue && addRecord) {
            throw new DggBaseException("企业id不正确");
        }
    }
}

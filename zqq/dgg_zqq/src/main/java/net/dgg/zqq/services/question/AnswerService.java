package net.dgg.zqq.services.question;

import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.constant.UserIdKeyConstant;
import net.dgg.zqq.dao.question.AnswerDao;
import net.dgg.zqq.dao.question.AnswerExtDao;
import net.dgg.zqq.dao.question.QuestionDao;
import net.dgg.zqq.dao.question.QuestionExtDao;
import net.dgg.zqq.entity.question.Answer;
import net.dgg.zqq.entity.question.Question;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AnswerService{

    @Autowired
    private AnswerDao answerDao;
    @Autowired
    private AnswerExtDao answerExtDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionExtDao questionExtDao;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserRecorderDAO userRecorderDAO;

    public Answer findById (String id){
        Assert.notNull(id, "问题ID不能为空");
        return answerDao.findById(Long.parseLong(id));
    }

    public void save(Answer answer) {
        //保存问答案操作
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");
        Assert.hasText(answer.getAnswer(), "answer不能为空！");
        Assert.notNull(answer.getQuestionId(),"关联答案时必须指定问题");

        //判断是否存在ID
        if(answer.getId()!=null){
            //修改
            Answer answerSys = answerDao.findById(answer.getId());
            answerSys.setAnswer(answer.getAnswer());
            answerSys.setUpdaterUser(userEntity);
            answerDao.update(answerSys);
        }else{
            //新增
            answer.setAnswerWay("1");  //内部回答
            answer.setStatus(StatusConstant.ENABLE);
            answer.setFlag(1);
            Long answerId = KeyWorker.nextId();
            answer.setId(answerId);
            answer.setCreateUser(userEntity);
            answerDao.save(answer);

            //修改问题的最佳答案ID
            Question question = questionDao.findById(answer.getQuestionId());
            question.setBestAnswerId(answerId.toString());
            question.setAnswerCount(1);
            questionDao.update(question);

        }


    }

}

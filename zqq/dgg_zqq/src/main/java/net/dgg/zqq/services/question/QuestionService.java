package net.dgg.zqq.services.question;

import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.constant.UserIdKeyConstant;
import net.dgg.zqq.dao.TreeBookDao;
import net.dgg.zqq.dao.question.AnswerDao;
import net.dgg.zqq.dao.question.AnswerExtDao;
import net.dgg.zqq.dao.question.QuestionDao;
import net.dgg.zqq.dao.question.QuestionExtDao;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.entity.question.Answer;
import net.dgg.zqq.entity.question.Question;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionExtDao questionExtDao;
    @Autowired
    private AnswerDao answerDao;
    @Autowired
    private AnswerExtDao answerExtDao;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserRecorderDAO userRecorderDAO;

    @Autowired
    private TreeBookDao treeBookDao;


    public void saveQuestion(Question question) {
        //保存操作
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.hasText(question.getQuestion(), "question不能为空！");
        Assert.hasText(question.getQuestionAppend(), "questionAppend不能为空！");
        Assert.hasText(question.getKeyWord(), "keyWord不能为空!");
        Assert.hasText(question.getTypeLevel1Code(), "typeLevel1Code不能为空!");
        Assert.hasText(question.getTypeLevel2Code(), "typeLevel2Code不能为空!");
        Assert.hasText(question.getTypeLevel3Code(), "typeLevel3Code不能为空!");

       if(question.getCreateWay()==null || question.getCreateWay()==""){
            question.setCreateWay("1");//内部创建
       }
       if(question.getRecommend()==null){
            question.setRecommend(0);  //默认推荐
       }

       question.setFlag(1);
       question.setStatus(StatusConstant.ENABLE);
       question.setId(KeyWorker.nextId());
       question.setCreateUser(userEntity);
       question.setViewTimes(0);
       question.setAnswerCount(0);
        question.setNumber(KeyWorker.nextId() + "");

       questionDao.save(question);

    }

    //修改
    public void updateQuestion(Question question) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.hasText(question.getQuestion(), "question不能为空！");
        Assert.hasText(question.getQuestionAppend(), "questionAppend不能为空！");
        Assert.hasText(question.getKeyWord(), "keyWord不能为空!");
        Assert.hasText(question.getTypeLevel1Code(), "typeLevel1Code不能为空!");
        Assert.hasText(question.getTypeLevel2Code(), "typeLevel2Code不能为空!");
        Assert.hasText(question.getTypeLevel3Code(), "typeLevel3Code不能为空!");

        if(question.getCreateWay()==null || question.getCreateWay()==""){
            question.setCreateWay("1");//内部创建
        }
        if(question.getRecommend()==null){
            question.setRecommend(0); //推荐
        }

        Question questionOld = questionDao.findById(question.getId());
        Assert.notNull(questionOld, "未查询到数据！");

        questionOld.setQuestion(question.getQuestion());
        questionOld.setQuestionAppend(question.getQuestionAppend());
        questionOld.setKeyWord(question.getKeyWord());
        questionOld.setTypeLevel1Code(question.getTypeLevel1Code());
        questionOld.setTypeLevel2Code(question.getTypeLevel2Code());
        questionOld.setTypeLevel3Code(question.getTypeLevel3Code());
        questionOld.setCreateWay(question.getCreateWay());
        questionOld.setRecommend(question.getRecommend());
        questionOld.setUpdaterUser(userEntity);

        questionDao.update(questionOld);
    }

    public void deleteQuestion(String id) {
        questionDao.deleteById(Long.parseLong(id));
    }

    public Question findById(Long id) {
            return questionDao.findById(id);
    }

    public List<Question> findQuestionByPage(Map map) {
        return questionDao.query(map);
    }


    public Map<String,Object> pageQueryByKeyWord(String keyword,String typeLevel1Code,Integer pageSize, Integer pageNum) {

        Assert.hasText(keyword, "关键字不能不能为空！");
        Assert.hasText(typeLevel1Code, "服务一级分类不能为空！");
        if(pageSize==null || pageSize<0){
            pageSize=10; //如果不设置 默认查询10条
        }
        if(pageNum==null || pageNum<0){
            pageNum=1;  //如果不设置页码 默认查询第一页
        }
        Integer start = (pageNum-1)*pageSize;
        Integer limit = pageSize;
        Map map = new HashMap();
        map.put("start",start);
        map.put("limit",limit);
        map.put("typeLevel1Code",typeLevel1Code);
        map.put("question",keyword);
        map.put("recommend",0); //查询推荐的问题
        map.put("flag",1);
        map.put("status",StatusConstant.ENABLE);
        map.put("answerCount", 1);

        List<Map<String, Object>> list = getMaps2(map);

        Map mapLast = new HashMap();
        mapLast.put("list",list);

        Map countMap = new HashMap();
        countMap.put("typeLevel1Code",typeLevel1Code);
        countMap.put("question",keyword);
        countMap.put("recommend",0); //查询推荐的问题
        countMap.put("flag",1);
        countMap.put("status",StatusConstant.ENABLE);

        Integer count = questionExtDao.countKeyword(countMap);
        mapLast.put("count", count);

        return mapLast;
    }


    public List<Map<String, Object>> pageQueryByTypeLevel3Code(String typeLevel1Code, String typeLevel3Code, Integer pageSize, Integer pageNum) {
        if(pageSize==null || pageSize<0){
            pageSize=10; //如果不设置 默认查询10条
        }
        if(pageNum==null || pageNum<0){
            pageNum=1;  //如果不设置页码 默认查询第一页
        }
        Integer start = (pageNum-1)*pageSize;
        Integer limit = pageSize;
        Map map = new HashMap();
        map.put("start",start);
        map.put("limit",limit);
        if (!StringUtils.isEmpty(typeLevel1Code)) {
            map.put("typeLevel1Code", typeLevel1Code);
        }
        if (!StringUtils.isEmpty(typeLevel3Code)) {
            //map.put("typeLevel3Code", typeLevel3Code);

            List<TreeBook> treeBookByCodes = treeBookDao.findTreeBookByCodes(typeLevel3Code);
            Assert.isTrue(treeBookByCodes.size() == 1, "typeLevel3Code传递错误");
            TreeBook treeBook = treeBookByCodes.get(0);
            String typeLevel2Code = treeBook.getPcode();
            List<TreeBook> treeBookByCodes2 = treeBookDao.findTreeBookByCodes(typeLevel2Code);
            Assert.isTrue(treeBookByCodes.size() == 1, "typeLevel3Code传递错误");
            TreeBook treeBook1 = treeBookByCodes2.get(0);
            String typeLevel1Code1 = treeBook1.getPcode();
            map.put("typeLevel1Code", typeLevel1Code1);

        }
        map.put("recommend",0); //查询推荐的问题
        map.put("flag",1);
        map.put("status",StatusConstant.ENABLE);
        map.put("answerCount", 1);

        List<Map<String, Object>> list = getMaps(map);

        return list;
    }

    public Map<String,Object> pageQueryByTypeLevel1Code(String typeLevel1Code, Integer pageSize, Integer pageNum) {
        Assert.hasText(typeLevel1Code, "服务一级分类不能为空！");
        if(pageSize==null || pageSize<0){
            pageSize=10; //如果不设置 默认查询10条
        }
        if(pageNum==null || pageNum<0){
            pageNum=1;  //如果不设置页码 默认查询第一页
        }
        Integer start = (pageNum-1)*pageSize;
        Integer limit = pageSize;
        Map map = new HashMap();
        map.put("start",start);
        map.put("limit",limit);
        map.put("typeLevel1Code",typeLevel1Code);
        map.put("recommend",0); //查询推荐的问题
        map.put("flag",1);
        map.put("status",StatusConstant.ENABLE);
        map.put("answerCount", 1);

        List<Map<String, Object>> list = getMaps2(map);
        Map mapLast = new HashMap();
        mapLast.put("list",list);

        Map countMap = new HashMap();
        countMap.put("typeLevel1Code",typeLevel1Code);
        countMap.put("recommend",0); //查询推荐的问题
        countMap.put("flag",1);
        countMap.put("status",StatusConstant.ENABLE);

        Integer count = questionExtDao.countKeyword(countMap);
        mapLast.put("count", count);
        return mapLast;
    }

    private List<Map<String, Object>> getMaps2(Map map) {
        List<Question> questions = questionExtDao.selectByKeyWord(map);
        List<Map<String,Object>> list = new ArrayList<>();
        if (questions == null || questions.size() < 1) {
            return list;
        }
        for (Question question : questions) {
            Map<String,Object> mapResult = new HashMap<>();
            mapResult.put("question",question);
            if(question.getBestAnswerId()!=null && question.getBestAnswerId()!=""){
                Answer answer = answerDao.findById(Long.parseLong(question.getBestAnswerId()));
                if(answer!=null){
                    mapResult.put("answer",answer);
                }
            }

            list.add(mapResult);

        }
        return list;
    }


    private List<Map<String, Object>> getMaps(Map map) {
        List<Question> questions = questionExtDao.selectByKeyWord(map);
        List<Map<String,Object>> list = new ArrayList<>();
        if (questions == null || questions.size() < 1) {
            return list;
        }
        for (Question question : questions) {
            Map<String,Object> mapResult = new HashMap<>();
            mapResult.put("questionId",question.getId());
            mapResult.put("questionText",question.getQuestion());
            if(question.getBestAnswerId()!=null && question.getBestAnswerId()!=""){
                Answer answer = answerDao.findById(Long.parseLong(question.getBestAnswerId()));
                if(answer!=null && (answer.getAnswer()!=null && answer.getAnswer()!="")){
                    mapResult.put("answerText",answer.getAnswer());
                }
            }

            list.add(mapResult);

        }
        return list;
    }

    public List<Map> pageQuery(Map query) {
        query.put("status", StatusConstant.ENABLE);
        Integer count = this.questionExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.questionExtDao.queryMap(query);
        }else{
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.notNull(id, "id不能为空！");
        Question question = questionDao.findById(id);
        Assert.notNull(question,"未查询到数据");

        question.setFlag(0);
        question.setUpdaterUser(userEntity);
        questionDao.update(question);

        // 删除问题下的答案
        List<Answer> list = answerExtDao.findByQuestionId(id);
        if(list!=null && list.size()>0){
            for (Answer answer : list) {
                answer.setFlag(0);
                answer.setQuestionId(null);
                answer.setUpdaterUser(userEntity);
                answerDao.update(answer);
            }

        }

    }


    public Map<String,Object> findQuestionAndAnswerById(Long id) {
        Assert.notNull(id,"问题ID不能为空");
        Map<String,Object> map = new HashMap<>();
        Question question = questionDao.findById(id);
        Assert.notNull(question,"没有查询到对应的问题");
        map.put("question",question);
        if(question.getBestAnswerId()!=null && question.getBestAnswerId()!=""){
            Answer answer = answerDao.findById(Long.parseLong(question.getBestAnswerId()));
            if(answer!=null){
                map.put("answer",answer);
            }else{
                map.put("answer",null);
            }
        }else{
            map.put("answer",null);
        }

        //更新浏览次数
        if(question.getViewTimes()!=null && question.getViewTimes()>=0){
            question.setViewTimes(question.getViewTimes()+1);
            questionDao.update(question);
        }
        return map;

    }
}

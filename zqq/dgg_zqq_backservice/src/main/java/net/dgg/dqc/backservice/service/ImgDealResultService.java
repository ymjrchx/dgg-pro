package net.dgg.dqc.backservice.service;

import net.dgg.dqc.backservice.dao_mybatis.ImgDealResultDao;
import net.dgg.dqc.backservice.entity.ImgDealResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * @author 刘阳
 * @ClassName <ImgDealResultService>
 * @despriction 图片处理结果 service
 * @create 2018/08/13 16:32
 **/
@Service
public class ImgDealResultService {
    @Autowired
    private ImgDealResultDao imgDealResultDao;

    /**
     * @param imgDealResult
     */
    @Transactional
    public void save(ImgDealResult imgDealResult) {
        this.imgDealResultDao.save(imgDealResult);
    }

    public Integer countFailResult() {
        return this.imgDealResultDao.count(new HashMap() {{
            put("result", 2);
        }});
    }

    public List<ImgDealResult> getFailResult(Integer start, Integer limit) {
        return this.imgDealResultDao.query(new HashMap() {{
            put("result", 2);
            put("limit", limit);
            put("start", start);
        }});
    }

    @Transactional
    public void updateResult(ImgDealResult imgDealResult) {
        this.imgDealResultDao.update(imgDealResult);
    }


}

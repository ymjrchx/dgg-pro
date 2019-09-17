package net.dgg.dqc.backservice.dao_mongodb;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * @author 刘阳
 * @ClassName <SpringDataPageable>
 * @despriction
 * @create 2018/07/25 19:01
 **/

public class SpringDataPageable implements Serializable, Pageable {

    // 当前页
    private Integer pagenumber = 1;
    // 当前页面条数
    private Integer pagesize = 10;
    //排序条件
    private Sort sort;

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    // 当前页面
    @Override
    public int getPageNumber() {
        return getPagenumber();
    }

    // 每一页显示的条数
    @Override
    public int getPageSize() {
        return getPagesize();
    }

    // 第二页所需要增加的数量
    @Override
    public int getOffset() {
        return (getPagenumber() - 1) * getPagesize();
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(this.pagenumber + 1);
        pageable.setSort(this.sort);
        pageable.setPagesize(this.pagesize);
        return pageable;
    }

    @Override
    public Pageable previousOrFirst() {
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(this.pagenumber - 1);
        pageable.setPagesize(this.pagesize);
        pageable.setSort(this.sort);
        return pageable;
    }

    @Override
    public Pageable first() {
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(1);
        pageable.setPagesize(this.pagesize);
        pageable.setSort(this.sort);
        return pageable;

    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    public Integer getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(Integer pagenumber) {
        this.pagenumber = pagenumber == null || pagenumber <= 0 ? 1 : pagenumber;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }
}



package net.dgg.dqc.backservice.framework.mongo;

import java.util.List;

/**
 * 翻页Dto对象。
 */
public class Page3Dto<T> implements DTO {
  private int pageIndex;
  private int pageSize;
  private int totalPageCount;
  private long totalRowCount;
  private List<T> data;

  public Page3Dto() {
  }

  public Page3Dto(int no, int size, long num) {
    this.setPageIndex(no);
    this.setPageSize(size);
    this.setTotalRowCount(num);
    int pageCount = (int) num / size;
    if (num % size > 0) {
      pageCount++;
    }
    this.setTotalPageCount(pageCount);
  }

  /**
   * 当前页面
   */
  public int getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   * 每页大小
   */
  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * 总页数。
   */
  public int getTotalPageCount() {
    return totalPageCount;
  }

  public void setTotalPageCount(int totalPageCount) {
    this.totalPageCount = totalPageCount;
  }

  /**
   * 翻页的数据。
   */
  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }


  public long getTotalRowCount() {
    return totalRowCount;
  }

  public void setTotalRowCount(long totalRowCount) {
    this.totalRowCount = totalRowCount;
  }
}

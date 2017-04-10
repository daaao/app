package io.zhijian.base.model.response;

import java.util.List;

/**
 * 分页响应
 * @param
 */
public class Page<T> {

    /*** 总数量 */
    private Integer total = 0;
    /*** 总页数 */
    private Integer pages = 0;
    /*** 每页显示数量 */
    private Integer size = 0;
    /*** 当前页码 */
    private Integer pagination = 0;
    /*** 是否有上一页 */
    private Boolean hasPrev = false;
    /*** 是否有下一页 */
    private Boolean hasNext = false;

    /*** 数据结果集 */
    private List<T> result;

    public Page() {
    }

    public Page(Integer totalCount, Integer totalPage, Integer size, Integer pagination, Boolean hasPrev, Boolean hasNext, List<T> result) {
        this.total = totalCount;
        this.pages = totalPage;
        this.size = size;
        this.pagination = pagination;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPagination() {
        return pagination;
    }

    public void setPagination(Integer pagination) {
        this.pagination = pagination;
    }

    public Boolean getHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(Boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "total=" + total +
                ", pages=" + pages +
                ", size=" + size +
                ", pagination=" + pagination +
                ", hasPrev=" + hasPrev +
                ", hasNext=" + hasNext +
                ", result=" + result +
                '}';
    }
}

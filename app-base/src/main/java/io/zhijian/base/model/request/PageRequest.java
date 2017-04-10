package io.zhijian.base.model.request;

/**
 * Created by Hao on 2017-03-23.
 */
public class PageRequest {

    /**
     * 当前页码
     */
    private Integer page = 1;
    /**
     * 每页数量
     */
    private Integer rows = 10;
    /**
     * 排序规则：升序=asc，降序=desc
     */
    private String order;// asc desc
    /**
     * 排序字段名称
     */
    private String sort;

    public PageRequest() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * 设置排序规则
     *
     * @param order 升序=asc，降序=desc
     * @return
     */
    public PageRequest order(String order) {
        setOrder(order);
        return this;
    }

    /**
     * 设置排序字段
     *
     * @param orderBy 字段名称
     * @return
     */
    public PageRequest orderBy(String orderBy) {
        setSort(orderBy);
        return this;
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "page=" + page +
                ", rows=" + rows +
                ", order='" + order + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}

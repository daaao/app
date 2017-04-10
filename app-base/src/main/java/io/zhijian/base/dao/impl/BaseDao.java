package io.zhijian.base.dao.impl;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.model.request.PageRequest;
import io.zhijian.base.model.response.Page;
import io.zhijian.utils.Reflections;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Hao on 2017-03-23.
 */
public class BaseDao<T, PK extends Serializable> implements IBaseDao<T, PK> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> entityClass;

    public BaseDao() {
        this.entityClass = Reflections.getClassGenricType(getClass());
    }

    public String getIdName() {
        ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Query createQuery(String hql, Map params) {
        Query query = getSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
//            query.setProperties(params);
            query = setParameters(query, params);
        }
        return query;
    }

    @Override
    public NativeQuery createNativeQuery(String sql, Map params) {
        return createNativeQuery(sql, params, null);
    }

    @Override
    public NativeQuery createNativeQuery(String sql, Map params, Class clazz) {
        NativeQuery query = (clazz == null ? getSession().createNativeQuery(sql) : getSession().createNativeQuery(sql, clazz));
        if (params != null && !params.isEmpty()) {
//            query.setProperties(params);
            query = (NativeQuery) setParameters(query, params);
        }
        /*if (clazz != null) {
            query.setResultTransformer(new BeanTransformerAdapter<>(clazz));
        }*/
        return query;
    }

    @Override
    public Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        Criterion deletedCriterion = Restrictions.eq("isDeleted", 0);
        criteria.add(deletedCriterion);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    private Page getPage(Integer pageIndex,Integer pageSize,int totalCount, List result) {
        // 计算分页总页数
        int totalPage = pageSize == null || pageSize<=0 ? 0 : (totalCount - 1) / pageSize + 1;
        boolean hasPrevPage = pageIndex ==null || pageIndex<=0 || pageIndex == 1 ? false : true;
        boolean hasNextPage = pageIndex ==null || pageIndex<=0 || pageIndex == totalPage || totalPage == 0 ? false : true;
        return  new Page(totalCount, totalPage, pageSize, pageIndex, hasPrevPage, hasNextPage, result);
    }

    @Override
    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void save(Collection<T> entities) {
        for (T entity : entities) {
            save(entity);
        }
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    public void delete(PK id) {
        delete(find(id));
    }

    @Override
    public int delete(String hql, Map params) {
        return createQuery(hql, params).executeUpdate();
    }

    @Override
    public void delete(Collection<T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public int remove(PK id) {
        String hql = " update " + entityClass.getName() + " t set t.isDeleted=1 where t.id=:id ";
        HashMap params = new HashMap();
        params.put("id", id);
        Query query = createQuery(hql, params);
        return query.executeUpdate();
    }


    @Override
    public void update(T entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public T get(PK id) {
        Criterion idCriterion = Restrictions.eq("id", id);
        Criterion deletedCriterion = Restrictions.ne("isDeleted", 1);//未删除，isDeleted != 1
        return (T) createCriteria(idCriterion, deletedCriterion).uniqueResult();
    }

    @Override
    public T find(PK id) {
        return getSession().load(entityClass, id);
    }

    @Override
    public T find(String propertyName, Object value) {
        Criterion criterion = Restrictions.eq(propertyName, value);
        Criterion deletedCriterion = Restrictions.ne("isDeleted", 1);//未删除，isDeleted != 1
        return (T) createCriteria(criterion, deletedCriterion).uniqueResult();
    }

    @Override
    public T findUnique(Criteria criteria) {
        return (T) criteria.uniqueResult();
    }

    @Override
    public <X> X findUnique(String hql, Map params) {
        return (X) createQuery(hql, params).uniqueResult();
    }

    @Override
    public <X> X findUnique(String sql, Map params, Class clazz) {
        Query query = createNativeQuery(sql, params, clazz);
        return (X) query.uniqueResult();
    }

    @Override
    public List<T> find(Collection<PK> idList) {
        return find(Restrictions.in(getIdName(), idList));
    }

    @Override
    public List<T> findAll() {
        return find();
    }

    @Override
    public List<T> findAll(String orderByProperty, boolean isAsc) {
        Criteria c = createCriteria();
        if (isAsc) {
            c.addOrder(Order.asc(orderByProperty));
        } else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value) {
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    @Override
    public List<T> find(Criteria criteria) {
        return criteria.list();
    }

    @Override
    public List<T> find(Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    @Override
    public <X> List<X> find(String hql, Map params) {
        return createQuery(hql, params).list();
    }

    @Override
    public List find(String sql, Map params, Class clazz) {
        /*Query query = createNativeQuery(sql, params);
        if(clazz!=null){
            query.setResultTransformer(new BeanTransformerAdapter<>(clazz));
        }*/
        Query query = createNativeQuery(sql, params, clazz);
        return query.list();
    }

    @Override
    public Page find(String hql, Map params, PageRequest pageRequest) {
        //绑定排序参数
        hql = order(hql, pageRequest);

        Query q = createQuery(hql, params);
        int totalCount = getTotalCount(hql, params);
        q.setFirstResult((pageRequest.getPage() - 1) * pageRequest.getRows());
        q.setMaxResults(pageRequest.getRows());
        List result = q.list();
        return getPage(pageRequest.getPage(), pageRequest.getRows(), totalCount, result);
    }

    @Override
    public Page find(PageRequest pageRequest, Criteria criteria) {
        int totalCount = getTotalCount(criteria);
        criteria = bindPageParameters(criteria, pageRequest);
        List result = criteria.list();
        return getPage(pageRequest.getPage(), pageRequest.getRows(), totalCount, result);
    }

    @Override
    public Page find(String sql, Map params, PageRequest pageRequest, Class clazz) {
        //绑定排序参数
        sql = order(sql, pageRequest);

        Query q = createNativeQuery(sql, params, clazz);
        int totalCount = getSqlTotalCount(sql, params);
        q.setFirstResult((pageRequest.getPage() - 1) * pageRequest.getRows());
        q.setMaxResults(pageRequest.getRows());
        List result = q.list();
        return getPage(pageRequest.getPage(), pageRequest.getRows(), totalCount, result);
    }

    /**
     * 获得Criteria查询所能获得的对象总数
     * @param c
     * @return
     */
    protected Integer getTotalCount(Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();

        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            orderEntries = (List) Reflections.getFieldValue(impl, "orderEntries");
            Reflections.setFieldValue(impl, "orderEntries", new ArrayList());
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        // 执行Count查询
        Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
        long totalCount = (totalCountObject != null) ? totalCountObject : 0;

        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        c.setProjection(projection);

        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            Reflections.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }
        return Integer.parseInt(String.valueOf(totalCount));
    }

    /**
     * 获取总记录数
     * @param hql  hql语句
     * @param params
     * @return
     */
    protected Integer getTotalCount(String hql, Map params) {
        String countHql = prepareCountHql(hql);
        try {
            Long count = findUnique(countHql, params);
            return Integer.parseInt(String.valueOf(count));
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    /**
     * 获取总记录数
     * @param sql    sql语句
     * @param params
     * @return
     */
    protected Integer getSqlTotalCount(String sql, Map params) {
        String countSql = "select count(*) from ( " + sql + " ) temp";
        Query query = createNativeQuery(countSql, params);
        return ((Number) query.uniqueResult()).intValue();
    }

    private String prepareCountHql(String orgHql) {
        String fromHql = orgHql;
        //select子句与order by子句会影响count查询,进行简单的排除.
        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");

        String countHql = "select count(*) " + fromHql;
        return countHql;
    }

    /**
     * 绑定分页参数到Criteria对象.
     */
    protected Criteria bindPageParameters(Criteria c, PageRequest pageRequest) {
        //hibernate的firstResult的序号从0开始
        Integer pageIndex = pageRequest.getPage();
        Integer pageSize = pageRequest.getRows();
        String order = pageRequest.getOrder();
        String orderBy = pageRequest.getSort();

        c.setFirstResult((pageIndex - 1) * pageSize);
        c.setMaxResults(pageSize);

        if (orderBy != null) {
            String[] orderByArray = StringUtils.split(orderBy, ',');
            String[] orderArray = StringUtils.split(order, ',');
            for (int i = 0; i < orderByArray.length; i++) {
                if ("asc".equals(orderArray[i])) {
                    c.addOrder(Order.asc(orderByArray[i]));
                } else {
                    c.addOrder(Order.desc(orderByArray[i]));
                }
            }
        }
        return c;
    }

    /**
     * 绑定分页参数到Criteria对象.
     */
    protected String order(String queryString, PageRequest pageRequest) {
        //hibernate的firstResult的序号从0开始
        String order = pageRequest.getOrder();
        String orderBy = pageRequest.getSort();

        if (orderBy != null) {
            StringBuffer sb = new StringBuffer(queryString);
            String[] orderByArray = StringUtils.split(orderBy, ',');
            String[] orderArray = StringUtils.split(order, ',');
            for (int i = 0; i < orderByArray.length; i++) {
                if ("asc".equalsIgnoreCase(orderArray[i])) {
                    if(i == 0){
                        sb.append(" order by ").append(orderByArray[i]).append(" asc ");
                    }else{
                        sb.append(" , ").append(orderByArray[i]).append(" asc ");
                    }
                } else {
                    if(i == 0){
                        sb.append(" order by ").append(orderByArray[i]).append(" desc ");
                    }else{
                        sb.append(" , ").append(orderByArray[i]).append(" desc ");
                    }
                }
            }
            queryString = sb.toString();
        }
        return queryString;
    }

    public Query setParameters(Query query, Map params) {
        if (params != null && !params.isEmpty()) {
            Iterator it = params.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                query.setParameter(key.toString(), params.get(key));
            }
        }
        return query;
    }
}

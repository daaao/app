package io.zhijian.base.dao;

import io.zhijian.base.model.response.Page;
import io.zhijian.base.model.request.PageRequest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Hao on 2017-03-23.
 */
public interface IBaseDao<T, PK extends Serializable> {

    public Session getSession();

    public Query createQuery( String hql,  Map params);
    public NativeQuery createNativeQuery( String sql,  Map params);
    public NativeQuery createNativeQuery( String sql,  Map params, Class clazz);
    public Criteria createCriteria( Criterion... criterions);

    public void save( T entity);
    public void save( Collection<T> entities);

    public void delete( T entity);
    public void delete( PK id);
    public int delete( String hql,  Map params);
    public void delete( Collection<T> entities);
    public int remove( PK id);

    public void update( T entity);

    public T get( PK id);
    public T find( PK id);
    public T find( String propertyName,  Object value);
    public T findUnique( Criteria criteria);
    public <X> X findUnique( String hql,  Map params);
    public <X> X findUnique( String sql,  Map params, Class clazz);
    public List<T> find( Collection<PK> idList);
    public List<T> findAll();
    public List<T> findAll(String orderByProperty, boolean isAsc);
    public List<T> findByProperty( String propertyName,  Object value);
    public List<T> find( Criteria criteria);
    public List<T> find( Criterion... criterions);
    public <X> List<X> find( String hql,  Map params);
    public List find( String sql,  Map params, Class clazz);

    public Page find( String hql,  Map params,  PageRequest pageRequest);
    public Page find( PageRequest pageRequest,  Criteria criteria);
    public Page find( String sql,  Map params,  PageRequest pageRequest, Class clazz);

}

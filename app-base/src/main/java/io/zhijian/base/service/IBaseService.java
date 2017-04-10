package io.zhijian.base.service;

import io.zhijian.base.dao.IBaseDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Hao on 2017-03-24.
 */
public interface IBaseService<T, PK extends Serializable> {

    public abstract IBaseDao<T, PK> getEntityDao();

    public void save(T entity);

    public void save(Collection<T> entities);

    public void delete(T entity);

    public void delete(PK id);

    public void delete(Collection<T> entities);

    public int remove(PK id);

    public void update(T entity);

    public T find(PK id);

    public T find(String propertyName, Object value);

    public List<T> find(Collection<PK> idList);

    public List<T> findAll();

    public List<T> findAll(String orderByProperty, boolean isAsc);

    public List<T> findByProperty(String propertyName, Object value);

}

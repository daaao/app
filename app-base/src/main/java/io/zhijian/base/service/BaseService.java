package io.zhijian.base.service;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.model.response.Page;
import io.zhijian.utils.BeanCopier;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class BaseService<T, PK extends Serializable> implements IBaseService<T, PK> {

    public abstract IBaseDao<T, PK> getEntityDao();

    @Override
    @Transactional
    public void save(T entity) {
        getEntityDao().save(entity);
    }

    @Override
    @Transactional
    public void save(Collection<T> entities) {
        getEntityDao().save(entities);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        getEntityDao().delete(entity);
    }

    @Override
    @Transactional
    public void delete(PK id) {
        getEntityDao().delete(id);
    }

    @Override
    @Transactional
    public void delete(Collection<T> entities) {
        getEntityDao().delete(entities);
    }

    @Override
    @Transactional
    public int remove(PK id) {
        return getEntityDao().remove(id);
    }

    @Override
    @Transactional
    public void update(T entity) {
        getEntityDao().update(entity);
    }

    @Override
    public T find(PK id) {
        return getEntityDao().find(id);
    }

    @Override
    public T find(String propertyName, Object value) {
        return getEntityDao().find(propertyName, value);
    }

    @Override
    public List<T> find(Collection<PK> idList) {
        return getEntityDao().find(idList);
    }

    @Override
    public List<T> findAll() {
        return getEntityDao().findAll();
    }

    @Override
    public List<T> findAll(String orderByProperty, boolean isAsc) {
        return getEntityDao().findAll(orderByProperty, isAsc);
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value) {
        return getEntityDao().findByProperty(propertyName, value);
    }

    public <X> Page<X> convert(Page<T> source, Class<X> destinationClass) {
        Page<X> page = BeanCopier.copy(source, Page.class);
        List<X> result = BeanCopier.copy(source.getResult(), destinationClass);
        page.setResult(result);
        return page;
    }
}

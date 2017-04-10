package io.zhijian.system.dao.impl;

import io.zhijian.base.dao.impl.BaseDao;
import io.zhijian.system.dao.IDictionaryDao;
import io.zhijian.system.entity.Dictionary;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Dictionary DAO实现
 * @create 2017-4-3 17:45:00
 */
@Repository
public class DictionaryDao extends BaseDao<Dictionary, Integer> implements IDictionaryDao {

    public Dictionary findByTypeAndCode(String type, String code) {
        String className = Dictionary.class.getName();
        Map<String, Object> params = new HashMap<>();
        StringBuilder hql = new StringBuilder(" from ").append(className).append(" as s where 1=1 and s.isDeleted != 1 ");

        hql.append(" and type.code = :type ");
        params.put("type", type);

        hql.append(" and code = :code ");
        params.put("code", code);

        return findUnique(hql.toString(), params);
    }
}

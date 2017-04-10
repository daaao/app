package io.zhijian.system.dao;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.system.entity.Dictionary;

/**
 * Dictionary DAO接口
 * @create 2017-4-3 17:45:00
 */
public interface IDictionaryDao extends IBaseDao<Dictionary, Integer> {

    public Dictionary findByTypeAndCode(String type, String code);
}

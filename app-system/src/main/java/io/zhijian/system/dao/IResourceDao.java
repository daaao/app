package io.zhijian.system.dao;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.system.entity.Resource;

import java.util.List;

/**
 * Created by Hao on 2017-03-26.
 */
public interface IResourceDao extends IBaseDao<Resource, Integer> {

    public List<Resource> findResource(Integer roleId);

    public int deleteRoleResource(Integer resourceId, Integer roleId);

    public List<Resource> findResource(Integer parentId, String type);

    public List<Resource> findResourceByUser(String username, String type);
}

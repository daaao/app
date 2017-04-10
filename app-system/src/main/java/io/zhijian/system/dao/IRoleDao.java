package io.zhijian.system.dao;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.system.entity.Role;

import java.util.List;

/**
 * Created by Hao on 2017-03-26.
 */
public interface IRoleDao extends IBaseDao<Role, Integer> {

    public int deleteUserRole(Integer userId, Integer roleId);

    public List<Role> findRole(Integer userId);
}

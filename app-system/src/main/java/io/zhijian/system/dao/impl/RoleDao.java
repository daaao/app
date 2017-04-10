package io.zhijian.system.dao.impl;

import io.zhijian.base.dao.impl.BaseDao;
import io.zhijian.system.dao.IRoleDao;
import io.zhijian.system.entity.Role;
import io.zhijian.utils.Utils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hao on 2017-03-26.
 */
@Repository
public class RoleDao extends BaseDao<Role, Integer> implements IRoleDao {

    public int deleteUserRole(Integer userId, Integer roleId) {
        StringBuilder sql = new StringBuilder("delete from sys_user_role where 1=1 ");
        Map params = new HashMap();

        if (!Utils.isEmpty(userId)) {
            sql.append(" and user_id = :userId ");
            params.put("userId", userId);
        }

        if (!Utils.isEmpty(roleId)) {
            sql.append(" and role_id = :roleId ");
            params.put("roleId", roleId);
        }
        return createNativeQuery(sql.toString(), params).executeUpdate();
    }

    public List<Role> findRole(Integer userId) {
        StringBuilder sql = new StringBuilder("select r.* from sys_role r\n" +
                " inner join sys_user_role ur on r.id = ur.role_id\n" +
                " inner join sys_user u on u.id = ur.user_id\n" +
                " where u.id = :userId ");

        Map params = new HashMap();
        params.put("userId", userId);

        return find(sql.toString(), params, Role.class);
    }
}

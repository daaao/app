package io.zhijian.system.dao.impl;

import io.zhijian.base.dao.impl.BaseDao;
import io.zhijian.system.dao.IResourceDao;
import io.zhijian.system.entity.Resource;
import io.zhijian.utils.Utils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hao on 2017-03-26.
 */
@Repository
public class ResourceDao extends BaseDao<Resource, Integer> implements IResourceDao {

    public List<Resource> findResource(Integer roleId) {
        String sql = " select r.* from sys_resource r inner join sys_role_resource rr on r.id = rr.resource_id where rr.role_id = :roleId ";
        Map params = new HashMap<>();
        params.put("roleId", roleId);

        return find(sql, params, Resource.class);
    }

    public int deleteRoleResource(Integer resourceId, Integer roleId) {
        StringBuilder sql = new StringBuilder("delete from sys_role_resource where 1=1 ");
        Map params = new HashMap();

        if (!Utils.isEmpty(resourceId)) {
            sql.append(" and resource_id = :resourceId ");
            params.put("resourceId", resourceId);
        }

        if (!Utils.isEmpty(roleId)) {
            sql.append(" and role_id = :roleId ");
            params.put("roleId", roleId);
        }
        return createNativeQuery(sql.toString(), params).executeUpdate();
    }

    public List<Resource> findResource(Integer parentId, String type) {
        String className = Resource.class.getName();
        Map<String, Object> params = new HashMap<>();
        StringBuilder hql = new StringBuilder(" from ").append(className).append(" as s where 1=1 and s.isDeleted != 1 ");

        if (!Utils.isEmpty(parentId)) {
            hql.append(" and parent.id = :parentId ");
            params.put("parentId", parentId);
        }

        if (!Utils.isEmpty(type)) {
            hql.append(" and type = :type ");
            params.put("type", type);
        }
        return find(hql.toString(), params);
    }

    public List<Resource> findResourceByUser(String username, String type) {
        StringBuilder sql = new StringBuilder(" select distinct * from (\n" +
                " select p.* from sys_resource p\n" +
                " inner join sys_role_resource rr on p.id = rr.resource_id\n" +
                " inner join sys_role r on r.id = rr.role_id\n" +
                " inner join sys_user_role ur on ur.role_id = rr.role_id\n" +
                " inner join sys_user u on u.id = ur.user_id\n" +
                " where (u.username = :username) ");

        Map params = new HashMap();
        params.put("username", username);

        if(!Utils.isEmpty(type)){
            sql.append(" and p.type=:type ");
            params.put("type", type);
        }

        sql.append(" order by p.sequence, p.id ) p order by p.sequence ");

        return find(sql.toString(), params, Resource.class);
    }
}

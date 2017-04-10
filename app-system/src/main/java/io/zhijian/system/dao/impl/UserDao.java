package io.zhijian.system.dao.impl;

import io.zhijian.base.dao.impl.BaseDao;
import io.zhijian.base.model.request.PageRequest;
import io.zhijian.base.model.response.Page;
import io.zhijian.system.dao.IUserDao;
import io.zhijian.system.entity.User;
import io.zhijian.system.model.request.UserRequest;
import io.zhijian.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hao on 2017-03-26.
 */
@Repository
public class UserDao extends BaseDao<User, Integer> implements IUserDao {
    @Override
    public Page<User> findPage(UserRequest request) {
        Criteria criteria = createCriteria();

        if(!Utils.isEmpty(request.getUsername())){
            criteria.add(Restrictions.eq("username",request.getUsername()));
        }

        if(!Utils.isEmpty(request.getGender())){
            criteria.add(Restrictions.eq("gender",request.getGender()));
        }

        if(!Utils.isEmpty(request.getMobile())){
            criteria.add(Restrictions.eq("mobile",request.getMobile()));
        }
        return find(request, criteria);
    }

    public Page<User> findPage(Integer roleId, PageRequest pageRequest) {
        String sql = " select u.* from sys_user u inner join sys_user_role ur on u.id = ur.user_id where ur.role_id = :roleId ";
        Map params = new HashMap<>();
        params.put("roleId", roleId);

        Page<User> page = find(sql, params, pageRequest, User.class);
        return page;
    }
}

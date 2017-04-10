package io.zhijian.system.dao;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.model.request.PageRequest;
import io.zhijian.base.model.response.Page;
import io.zhijian.system.entity.User;
import io.zhijian.system.model.request.UserRequest;

/**
 * Created by Hao on 2017-03-26.
 */
public interface IUserDao extends IBaseDao<User, Integer> {

    public Page<User> findPage(UserRequest request);

    public Page<User> findPage(Integer roleId, PageRequest pageRequest);
}

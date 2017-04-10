package io.zhijian.system.service;

import io.zhijian.base.model.request.PageRequest;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.IBaseService;
import io.zhijian.system.entity.User;
import io.zhijian.system.model.request.UserRequest;
import io.zhijian.system.model.response.UserResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hao on 2017-03-26.
 */
public interface IUserService extends IBaseService<User, Integer> {

    @Transactional
    public UserResponse save(UserRequest request);

    @Transactional
    public UserResponse update(UserRequest request);

    @Transactional
    public Integer del(Integer id);

    public UserResponse get(String username);

    public User getUser(String username);

    public UserResponse get(Integer id);

    public List<UserResponse> getUsers(UserRequest request);

    public Page<UserResponse> getPage(UserRequest request);

    public Integer changeUserRole(Integer userId, List<Integer> targetRoles);

    public Page<UserResponse> getUsers(Integer roleId, PageRequest pageRequest);

    public Integer modifyPassword(Integer userId, String originalPassword, String newPassword);

    public UserResponse auth(String username, String password);
}

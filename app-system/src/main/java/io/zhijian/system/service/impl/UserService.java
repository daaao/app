package io.zhijian.system.service.impl;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.exception.ApplicationException;
import io.zhijian.base.exception.StatusCode;
import io.zhijian.base.model.request.PageRequest;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.BaseService;
import io.zhijian.log.annotation.Log;
import io.zhijian.system.dao.IRoleDao;
import io.zhijian.system.dao.IUserDao;
import io.zhijian.system.entity.Role;
import io.zhijian.system.entity.User;
import io.zhijian.system.exception.SystemError;
import io.zhijian.system.model.request.UserRequest;
import io.zhijian.system.model.response.UserResponse;
import io.zhijian.system.service.IUserService;
import io.zhijian.utils.BeanCopier;
import io.zhijian.utils.MD5Utils;
import io.zhijian.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Hao on 2017-03-26.
 */
@Service
public class UserService extends BaseService<User, Integer> implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Override
    public IBaseDao<User, Integer> getEntityDao() {
        return userDao;
    }

    @Override
    @Transactional
    @Log(module = "系统用户", description = "添加用户信息")
    public UserResponse save(UserRequest request) {
        User existing = userDao.find("username", request.getUsername());
        if (existing == null) {
            User user = BeanCopier.copy(request, User.class);

            user.setPassword(MD5Utils.getStringMD5(request.getPassword()));//密码进行MD5加密

            save(user);

            return BeanCopier.copy(user, UserResponse.class);
        } else {
            //用户名已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统用户", description = "更新用户信息")
    public UserResponse update(UserRequest request) {
        User existing = userDao.find("username", request.getUsername());
        if (existing != null) {
            existing.setEmail(request.getEmail());
            existing.setGender(request.getGender());
            existing.setMobile(request.getMobile());
            existing.setName(request.getName());
            existing.setUpdateTime(new Date());

            userDao.update(existing);

            return BeanCopier.copy(existing, UserResponse.class);
        } else {
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统用户", description = "删除用户信息")
    public Integer del(Integer id) {
        User existing = null;
        if(existing!=null){
            userDao.delete(id);
            return 1;
        }else{
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public UserResponse get(String username) {
        User existing = userDao.find("username", username);
        if(existing!=null){
            return BeanCopier.copy(existing, UserResponse.class);
        }else{
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public User getUser(String username) {
        User user = userDao.find("username", username);
        return user;
    }

    @Override
    public UserResponse get(Integer id) {
        User existing = userDao.get(id);
        if(existing!=null){
            return BeanCopier.copy(existing, UserResponse.class);
        }else{
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public List<UserResponse> getUsers(UserRequest request) {
        List<User> users = null;
        List<UserResponse> responses = BeanCopier.copy(users, UserResponse.class);
        return responses;
    }

    @Override
    public Page<UserResponse> getPage(UserRequest request) {
        Page<User> page = userDao.findPage(request);
        return convert(page, UserResponse.class);
    }

    @Override
    @Transactional
    @Log(module = "系统用户", description = "修改用户角色")
    public Integer changeUserRole(Integer userId, List<Integer> targetRoles) {
        if (!Utils.isEmpty(userId)) {
            int delRows = roleDao.deleteUserRole(userId, null);
            if (!Utils.isEmpty(targetRoles)) {
                Set<Role> roles = new HashSet<>();
                for (Integer roleId : targetRoles) {
                    roles.add(new Role(roleId));
                }
                User user = userDao.get(userId);
                user.setRoles(roles);
                userDao.save(user);
                return 1;
            }
        }
        return -1;
    }

    @Override
    public Page<UserResponse> getUsers(Integer roleId, PageRequest pageRequest) {
        Page<User> page = userDao.findPage(roleId, pageRequest);
        return convert(page, UserResponse.class);
    }

    /**
     * 修改密码
     * @param userId 用户ID
     * @param originalPassword 原密码
     * @param newPassword      新密码
     */
    @Override
    @Transactional
    @Log(module = "系统用户", description = "修改密码")
    public Integer modifyPassword(Integer userId, String originalPassword, String newPassword) {
        User existing = userDao.get(userId);
        if (existing != null) {
            String encryData = MD5Utils.getStringMD5(originalPassword);
            if (existing.getPassword().equals(encryData)) {//验证原密码是否正确
                existing.setPassword(MD5Utils.getStringMD5(newPassword));
                userDao.update(existing);
                return 1;
            } else {
                //原密码错误
                throw new ApplicationException(SystemError.ORIGINAL_PASSWORD_ERROR.getCode(), SystemError.ORIGINAL_PASSWORD_ERROR.getMessage());
            }
        } else {
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public UserResponse auth(String username, String password) {
        User existing = userDao.find("username", username);
        if (existing != null) {
            String encryData = MD5Utils.getStringMD5(password);
            if (existing.getPassword().equals(encryData)) {//验证密码是否正确
                return BeanCopier.copy(existing, UserResponse.class);
            } else {
                //密码错误
                throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(), SystemError.LOGIN_FAILED.getMessage());
            }
        } else {
            //用户不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }
}

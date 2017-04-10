package io.zhijian.system.service.impl;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.exception.ApplicationException;
import io.zhijian.base.exception.StatusCode;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.BaseService;
import io.zhijian.log.annotation.Log;
import io.zhijian.system.dao.IResourceDao;
import io.zhijian.system.dao.IRoleDao;
import io.zhijian.system.entity.Resource;
import io.zhijian.system.entity.Role;
import io.zhijian.system.entity.User;
import io.zhijian.system.model.request.RoleRequest;
import io.zhijian.system.model.response.RoleResponse;
import io.zhijian.system.service.IRoleService;
import io.zhijian.system.service.IUserService;
import io.zhijian.utils.BeanCopier;
import io.zhijian.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Role Service实现类
 * @create 2017-3-28 10:45:58
 */
@Service
public class RoleService extends BaseService<Role, Integer> implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IResourceDao resourceDao;

    @Autowired
    private IUserService userService;

    @Override
    public IBaseDao<Role, Integer> getEntityDao() {
        return roleDao;
    }

    @Override
    @Transactional
    @Log(module = "系统角色", description = "添加角色信息")
    public RoleResponse save(RoleRequest request) {
        Role existing = roleDao.find("code", request.getCode());
        if (existing == null) {//判断是否已存在
            Role role = BeanCopier.copy(request, Role.class);

            save(role);

            return BeanCopier.copy(role, RoleResponse.class);
        } else {
            //数据已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统角色", description = "修改角色信息")
    public RoleResponse update(RoleRequest request) {
        Role existing = roleDao.find("code", request.getCode());
        if (existing != null) {
            //设置要更新的字段
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            existing.setUpdateTime(new Date());

            roleDao.update(existing);

            return BeanCopier.copy(existing, RoleResponse.class);
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统角色", description = "删除角色信息")
    public Integer del(Integer id) {
        Role existing = roleDao.find(id);
        if (existing != null) {
            try {
                roleDao.delete(id);
                return 1;
            } catch (Exception exception) {
                if (exception instanceof SQLIntegrityConstraintViolationException) {
                    throw new ApplicationException(StatusCode.FAILED_DEPENDENCY.getCode(), StatusCode.FAILED_DEPENDENCY.getMessage());
                }
            }
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
        return -1;
    }

    @Override
    public RoleResponse get(Integer id) {
        Role existing = roleDao.get(id);
        if(existing!=null){
            return BeanCopier.copy(existing, RoleResponse.class);
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public List<RoleResponse> getRoles() {
        List<Role> roles = findAll();
        List<RoleResponse> responses = BeanCopier.copy(roles, RoleResponse.class);
        return responses;
    }

    @Override
    public Page<RoleResponse> getPage(RoleRequest request) {
        Page<Role> page = null;
        return convert(page, RoleResponse.class);
    }

    @Override
    public List<RoleResponse> getRoles(Integer userId) {
        Set<Role> roles = new HashSet<>();
        User user = userService.find(userId);
        if(user!=null){
            roles = user.getRoles();
        }
        List<RoleResponse> responses = BeanCopier.copy(roles, RoleResponse.class);
        return responses;
    }

    @Override
    @Transactional
    @Log(module = "系统角色", description = "删除角色下指定的用户")
    public int deleteUserRole(Integer userId, Integer roleId) {
        return roleDao.deleteUserRole(userId, roleId);
    }

    @Transactional
    @Log(module = "系统角色", description = "修改角色权限")
    public Integer changeRolePermission(Integer roleId, List<Integer> targetResources) {
        if (!Utils.isEmpty(roleId)) {
            int delRows = resourceDao.deleteRoleResource(null, roleId);
            if (!Utils.isEmpty(targetResources)) {
                Set<Resource> resources = new HashSet<>();
                for (Integer resourceId : targetResources) {
                    resources.add(new Resource(resourceId));
                }
                Role role = roleDao.get(roleId);
                role.setResources(resources);
                roleDao.save(role);
                return 1;
            }
        }
        return -1;
    }
}

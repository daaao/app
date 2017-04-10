package io.zhijian.system.service;

import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.IBaseService;
import io.zhijian.system.entity.Role;
import io.zhijian.system.model.request.RoleRequest;
import io.zhijian.system.model.response.RoleResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Role Service接口
 * @create 2017-3-28 10:45:58
 */
public interface IRoleService extends IBaseService<Role, Integer> {

    @Transactional
    public RoleResponse save(RoleRequest request);

    @Transactional
    public RoleResponse update(RoleRequest request);

    @Transactional
    public Integer del(Integer id);

    public RoleResponse get(Integer id);

    public List<RoleResponse> getRoles();

    public Page<RoleResponse> getPage(RoleRequest request);

    public List<RoleResponse> getRoles(Integer userId);

    public int deleteUserRole(Integer userId, Integer roleId);

    public Integer changeRolePermission(Integer roleId, List<Integer> targetResources);
}

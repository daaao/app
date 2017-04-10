package io.zhijian.app.controller.sys;

import io.zhijian.base.controller.BaseController;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.model.vo.DataGrid;
import io.zhijian.system.model.request.RoleRequest;
import io.zhijian.system.model.response.RoleResponse;
import io.zhijian.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Role控制器
 * @create 2017-3-28 10:45:58
 */
@RestController
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RoleResponse get(@PathVariable("id") Integer id) {
        return roleService.get(id);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RoleResponse add(RoleRequest request) {
        return roleService.save(request);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public RoleResponse update(RoleRequest request) {
        return roleService.update(request);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(@RequestParam ("id") Integer id) {
        return roleService.del(id);
    }

    /**
     * 查询分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<RoleResponse> getPage(RoleRequest request) {
        return roleService.getPage(request);
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public DataGrid getList() {
        List<RoleResponse> roles = roleService.getRoles();
        return buildDataGrid(roles, roles.size());
    }

    /**
     * 查询用户拥有的角色列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public List<RoleResponse> getRoleByUserId(@PathVariable("id") Integer userId) {
        List<RoleResponse> roles = roleService.getRoles(userId);
        return roles;
    }

    /**
     * 删除角色下的用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "{roleId}/user/{userId}/delete", method = RequestMethod.POST)
    public int deleteUserRole(@PathVariable("roleId") Integer roleId, @PathVariable("userId") Integer userId) {
        return roleService.deleteUserRole(userId, roleId);
    }

    /**
     * 修改角色拥有的权限
     *
     * @return
     */
    @RequestMapping(value = "{id}/resource/modify", method = RequestMethod.POST)
    public Integer changeUserRole(@PathVariable("id") Integer roleId, @RequestParam(required = false, name = "resources[]") List<Integer> resources) {
        return roleService.changeRolePermission(roleId, resources);
    }
}

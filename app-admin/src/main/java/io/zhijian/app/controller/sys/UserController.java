package io.zhijian.app.controller.sys;

import io.zhijian.base.controller.BaseController;
import io.zhijian.base.exception.ApplicationException;
import io.zhijian.base.exception.StatusCode;
import io.zhijian.base.model.request.PageRequest;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.model.vo.DataGrid;
import io.zhijian.system.model.request.UserRequest;
import io.zhijian.system.model.response.UserResponse;
import io.zhijian.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Hao
 * @create 2017-03-26
 */
@RestController
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 查询单个用户
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public UserResponse get(@RequestParam("username") String username) {
        return userService.get(username);
    }

    /**
     * 查询单个用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserResponse get(@PathVariable("id") Integer id) {
        return userService.get(id);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public UserResponse add(@Valid UserRequest user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApplicationException(StatusCode.BAD_REQUEST.getCode(), getValidateErrorMessage(bindingResult));
        }
        return userService.save(user);
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public UserResponse update(UserRequest user) {
        return userService.update(user);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(@RequestParam("id") Integer id) {
        return userService.del(id);
    }

    /**
     * 查询分页
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public DataGrid getPage(UserRequest user) {
        Page<UserResponse> page = userService.getPage(user);
        return super.buildDataGrid(page);
    }

    /**
     * 查询列表
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserResponse> getList(UserRequest user) {
        return null;
    }

    /**
     * 修改用户拥有的角色
     *
     * @return
     */
    @RequestMapping(value = "{id}/role/modify", method = RequestMethod.POST)
    public Integer changeUserRole(@PathVariable("id") Integer userId, @RequestParam(required = false, name = "roles[]") List<Integer> roles) {
        return userService.changeUserRole(userId, roles);
    }

    /**
     * 查询角色下的用户列表
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public DataGrid getUserByRoleId(@PathVariable("roleId") Integer roleId, PageRequest pageRequest) {
        Page<UserResponse> page = userService.getUsers(roleId, pageRequest);
        return buildDataGrid(page);
    }

    @RequestMapping(value = "{id}/modifypwd", method = RequestMethod.POST)
    public Integer modifyPassword(@PathVariable("id") Integer id, @RequestParam("originalPassword") String originalPassword, @RequestParam("newPassword") String newPassword) {
        return userService.modifyPassword(id, originalPassword, newPassword);
    }
}

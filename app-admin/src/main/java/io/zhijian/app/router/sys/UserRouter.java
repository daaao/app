package io.zhijian.app.router.sys;

import io.zhijian.base.router.BaseRouter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Hao on 2017-03-24.
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserRouter extends BaseRouter{

    @Override
    protected String getPrefix() {
        return "/admin/sys/user";
    }

    /**
     * 跳转到用户角色页面
     */
    @RequestMapping(value = "/{id}/role", method = RequestMethod.GET)
    public String role(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("id", id);
        return "/admin/sys/user/role";
    }

    /**
     * 跳转到修改密码页面
     */
    @RequestMapping(value = "/{id}/modifypwd", method = RequestMethod.GET)
    public String modifyPwd(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("id", id);
        return "/admin/sys/user/modifypwd";
    }
}

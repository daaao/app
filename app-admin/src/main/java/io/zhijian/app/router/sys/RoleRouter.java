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
@RequestMapping(value = "/admin/role")
public class RoleRouter extends BaseRouter{

    @Override
    protected String getPrefix() {
        return "/admin/sys/role";
    }

    /**
     * 跳转到角色用户页面
     */
    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    public String role(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("id", id);
        return "/admin/sys/role/user";
    }
}

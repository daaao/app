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
@RequestMapping(value = "/admin/permission")
public class PermissionRouter extends BaseRouter{

    @Override
    protected String getPrefix() {
        return "/admin/sys/permission";
    }

    /**
     * 跳转到新增页面
     */
    @RequestMapping(value = "/add/{menuId}", method = RequestMethod.GET)
    public String add(Model model, @PathVariable("menuId") Integer menuId) {
        model.addAttribute("menuId", menuId);
        return ADD;
    }
}

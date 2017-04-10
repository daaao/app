package io.zhijian.base.router;

import io.zhijian.base.common.Const;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Hao on 2017-03-24.
 */
public abstract class BaseRouter {

    protected abstract String getPrefix();

    protected final String ADD = getPrefix() + "/add";
    protected final String EDIT = getPrefix() + "/edit";
    protected final String VIEW = getPrefix() + "/view";
    protected final String LIST = getPrefix() + "/list";

    /**
     * 通用跳转到新增页面
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    protected String add() {
        return ADD;
    }

    /**
     * 通用跳转到编辑页面
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("id", id);
        return EDIT;
    }

    /**
     * 通用跳转到查看详情页面
     */
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable("id") String id) {
        model.addAttribute("id", id);
        return VIEW;
    }

    /**
     * 通用跳转到查看详情页面
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return LIST;
    }

    /**
     * 获取保存在Session中的用户对象
     * @param request
     * @param <T>
     * @return
     */
    protected <T> T getSessionUser(HttpServletRequest request) {
        return (T) request.getSession().getAttribute(Const.SESSION_USER);
    }
}

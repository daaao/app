package io.zhijian.app.controller.sys;

import io.zhijian.app.utils.TreeNodeUtils;
import io.zhijian.base.controller.BaseController;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.model.vo.DataGrid;
import io.zhijian.base.model.vo.TreeNode;
import io.zhijian.system.model.request.ResourceRequest;
import io.zhijian.system.model.response.ResourceResponse;
import io.zhijian.system.model.response.UserResponse;
import io.zhijian.system.service.IResourceService;
import io.zhijian.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource控制器
 *
 * @create 2017-3-28 10:45:58
 */
@RestController
@RequestMapping(value = "/sys/resource")
public class ResourceController extends BaseController {

    @Autowired
    private IResourceService resourceService;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResourceResponse get(@PathVariable("id") Integer id) {
        return resourceService.get(id);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResourceResponse add(ResourceRequest request) {
        return resourceService.save(request);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResourceResponse update(ResourceRequest request) {
        return resourceService.update(request);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public int delete(@PathVariable("id") int id) {
        return resourceService.del(id);
    }

    /**
     * 查询分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<ResourceResponse> getPage(ResourceRequest request) {
        return resourceService.getPage(request);
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public DataGrid getAll() {
        List<ResourceResponse> responses = resourceService.getResources();
        return buildDataGrid(responses, responses == null ? 0 : responses.size());
    }

    /**
     * 根据角色查询
     */
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public List<ResourceResponse> getResourceByRole(@PathVariable("roleId") Integer roleId) {
        List<ResourceResponse> responses = resourceService.getResources(roleId);
        return responses;
    }

    /**
     * 查询树列表 easyui combotree
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<TreeNode> getTree() {
        List<TreeNode> treeNodes = new ArrayList<>();

        TreeNode root = new TreeNode();
        root.setId(-1);
        root.setPid(null);
        root.setText("顶级菜单");
        treeNodes.add(root);

        List<ResourceResponse> responses = resourceService.getResources();
        for (ResourceResponse resource : responses) {
            if (resource != null && "1".equals(resource.getType())) {//菜单
                TreeNode treeNode = new TreeNode();
                treeNode.setId(resource.getId());
                treeNode.setPid(resource.get_parentId());
                treeNode.setText(resource.getName());
                treeNodes.add(treeNode);
            }
        }
        return TreeNodeUtils.buildTreeNode(treeNodes);
    }

    /**
     * 查询菜单权限列表
     */
    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public DataGrid getPermission(@RequestParam(required = false, name = "parentId") Integer parentId, @RequestParam(required = false, name = "type") String type) {
        List<ResourceResponse> responses = resourceService.getResources(parentId, type);
        return buildDataGrid(responses, responses == null ? 0 : responses.size());
    }

    /**
     * 查询菜单列表
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public DataGrid getMenus(@RequestParam(required = false, name = "parentId") Integer parentId, @RequestParam(required = false, name = "type") String type) {
        List<ResourceResponse> responses = resourceService.getResources(parentId, type);
        return buildDataGrid(responses, responses == null ? 0 : responses.size());
    }

    /**
     * 查询菜单列表
     */
    @RequestMapping(value = "/menus/my", method = RequestMethod.GET)
    public List<ResourceResponse> getMenus(@RequestParam(required = false, name = "username") String username, HttpServletRequest request) {
        if (Utils.isEmpty(username)) {
            UserResponse user = getSessionUser(request);
            if (user != null && !Utils.isEmpty(user.getUsername())) {
                username = user.getUsername();
            }
        }
        //查询我的菜单
        List<ResourceResponse> responses = resourceService.getResourceByUser(username, "1");
        return responses;
    }
}

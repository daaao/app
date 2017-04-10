package ${controllerPackageName};

import io.zhijian.base.controller.BaseController;
import io.zhijian.base.model.response.Page;
import ${modelPackageName}.request.${modelName}Request;
import ${modelPackageName}.response.${modelName}Response;
import ${servicePackageName}.I${modelName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * ${modelName}控制器
 * @create ${.now}
 */
@RestController
@RequestMapping(value = "/${lowerModelName}")
public class ${modelName}Controller extends BaseController {

    @Autowired
    private I${modelName}Service ${lowerModelName}Service;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ${modelName}Response get(@PathVariable("id") ${pkType} id) {
        return null;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ${modelName}Response add(${modelName}Request request) {
        return null;
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ${modelName}Response update(${modelName}Request request) {
        return null;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public ${pkType} delete(@PathVariable("id") int id) {
        return null;
    }

    /**
     * 查询分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<${modelName}Response> getPage(${modelName}Request request) {
        return null;
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<${modelName}Response> getList(${modelName}Request request) {
        return null;
    }
}

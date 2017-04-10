package io.zhijian.app.controller.sys;

import io.zhijian.base.controller.BaseController;
import io.zhijian.base.model.vo.DataGrid;
import io.zhijian.system.model.request.SystemLogRequest;
import io.zhijian.system.model.response.SystemLogResponse;
import io.zhijian.system.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SystemLog控制器
 * @create 2017-3-29 21:34:13
 */
@RestController
public class SystemLogController extends BaseController {

    @Autowired
    private ISystemLogService systemLogService;

    /**
     * 查询单个
     */
    @RequestMapping(value = "/sys/logs/{id}", method = RequestMethod.GET)
    public SystemLogResponse get(@PathVariable("id") Integer id) {
        return null;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/sys/logs", method = RequestMethod.POST)
    public SystemLogResponse add(SystemLogRequest request) {
        return null;
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/sys/logs", method = RequestMethod.PUT)
    @ResponseBody
    public SystemLogResponse update(SystemLogRequest request) {
        return null;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/sys/logs/{id}", method = RequestMethod.DELETE)
    public Integer delete(@PathVariable("id") int id) {
        return null;
    }

    /**
     * 查询分页
     */
    @RequestMapping(value = "/sys/logs/page", method = RequestMethod.GET)
    public DataGrid getPage(SystemLogRequest request) {
        return buildDataGrid(systemLogService.getPage(request));
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/sys/logs", method = RequestMethod.GET)
    public List<SystemLogResponse> getList(SystemLogRequest request) {
        return null;
    }
}

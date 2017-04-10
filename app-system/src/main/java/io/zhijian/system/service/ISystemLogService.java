package io.zhijian.system.service;

import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.IBaseService;
import io.zhijian.system.entity.SystemLog;
import io.zhijian.system.model.request.SystemLogRequest;
import io.zhijian.system.model.response.SystemLogResponse;

/**
 * SystemLog Service接口
 * @create 2017-3-29 21:34:13
 */
public interface ISystemLogService extends IBaseService<SystemLog, Integer> {

    public Page<SystemLogResponse> getPage(SystemLogRequest request);
}

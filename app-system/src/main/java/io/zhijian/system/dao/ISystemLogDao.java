package io.zhijian.system.dao;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.model.response.Page;
import io.zhijian.system.entity.SystemLog;
import io.zhijian.system.model.request.SystemLogRequest;
import io.zhijian.system.vo.TestCount;

import java.util.List;

/**
 * SystemLog DAO接口
 * @create 2017-3-29 21:34:13
 */
public interface ISystemLogDao extends IBaseDao<SystemLog, Integer> {

    public Page<SystemLog> findPage(SystemLogRequest request);

    public List<TestCount> findTest(String module);
}

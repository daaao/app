package io.zhijian.system.dao.impl;

import com.alibaba.fastjson.JSON;
import freemarker.template.TemplateException;
import io.zhijian.base.dao.impl.BaseDao;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.sql.SqlBuilder;
import io.zhijian.base.sql.SqlConfig;
import io.zhijian.base.utils.BeanTransformerAdapter;
import io.zhijian.system.dao.ISystemLogDao;
import io.zhijian.system.entity.SystemLog;
import io.zhijian.system.model.request.SystemLogRequest;
import io.zhijian.system.vo.TestCount;
import io.zhijian.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SystemLog DAO实现
 * @create 2017-3-29 21:34:13
 */
@Repository
public class SystemLogDao extends BaseDao<SystemLog, Integer> implements ISystemLogDao {
    @Override
    public Page<SystemLog> findPage(SystemLogRequest request) {
        Criteria criteria = createCriteria();

        if(!Utils.isEmpty(request.getUsername())){
            criteria.add(Restrictions.eq("username",request.getUsername()));
        }

        if(!Utils.isEmpty(request.getModule())){
            criteria.add(Restrictions.eq("module",request.getModule()));
        }

        if(!Utils.isEmpty(request.getIp())){
            criteria.add(Restrictions.eq("ip",request.getIp()));
        }

        if(!Utils.isEmpty(request.getDescription())){
            criteria.add(Restrictions.eq("description",request.getDescription()));
        }

        if(!Utils.isEmpty(request.getStartCreateTime())){
            criteria.add(Restrictions.ge("createTime",request.getStartCreateTime()));
        }
        if(!Utils.isEmpty(request.getEndCreateTime())){
            criteria.add(Restrictions.le("createTime",request.getEndCreateTime()));
        }

        return find(request, criteria);
    }

    /**
     * 测试SQLBuilder 的使用
     * 原理：使用freemarker模板定义要执行的sql文件，支持动态参数逻辑判断，构建SQL语句
     * @param module
     * @return
     */
    @Override
    public List<TestCount> findTest(String module) {
        try {
            Map params = new HashMap<>();
            params.put("module", module);

            String json = JSON.toJSONString(params);

            String sql = SqlBuilder.buildSql(SqlConfig.getSqlPath("select-test"), json);

            NativeQuery query = getSession().createNativeQuery(sql);
            query.setResultTransformer(new BeanTransformerAdapter<>(TestCount.class));
            setParameters(query, params);

            List<TestCount> result = query.list();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }
}

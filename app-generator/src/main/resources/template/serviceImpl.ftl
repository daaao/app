package ${serviceImplPackageName};

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.exception.ApplicationException;
import io.zhijian.base.exception.StatusCode;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.BaseService;
import ${daoPackageName}.I${modelName}Dao;
import ${entityPackageName}.${modelName};
import ${modelPackageName}.request.${modelName}Request;
import ${modelPackageName}.response.${modelName}Response;
import ${servicePackageName}.I${modelName}Service;
import io.zhijian.utils.BeanCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${modelName} Service实现类
 * @create ${.now}
 */
@Service
public class ${modelName}Service extends BaseService<${modelName}, ${pkType}> implements I${modelName}Service {

    @Autowired
    private I${modelName}Dao ${lowerModelName}Dao;

    @Override
    public IBaseDao<${modelName}, ${pkType}> getEntityDao() {
        return ${lowerModelName}Dao;
    }

    @Override
    @Transactional
    public ${modelName}Response save(${modelName}Request request) {
        ${modelName} existing = null;
        if (existing == null) {//判断是否已存在
            ${modelName} ${lowerModelName} = BeanCopier.copy(request, ${modelName}.class);

            save(${lowerModelName});

            return BeanCopier.copy(${lowerModelName}, ${modelName}Response.class);
        } else {
            //数据已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }

    @Override
    @Transactional
    public ${modelName}Response update(${modelName}Request request) {
        ${modelName} existing = null;
        if (existing != null) {
            //TODO 设置要更新的字段
            existing.setUpdateTime(new Date());

            ${lowerModelName}Dao.update(existing);

            return BeanCopier.copy(existing, ${modelName}Response.class);
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    @Transactional
    public Integer del(Integer id) {
        ${modelName} existing = null;
        if(existing!=null){
            int result = ${lowerModelName}Dao.remove(id);

            Integer response = result > 0 ? id : -1;

            return response;
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public ${modelName}Response get(${pkType} id) {
        ${modelName} existing = null;
        if(existing!=null){
            return BeanCopier.copy(existing, ${modelName}Response.class);
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public List<${modelName}Response> get${modelName}s(${modelName}Request request) {
        List<${modelName}> ${lowerModelName}s = null;
        List<${modelName}Response> responses = BeanCopier.copy(${lowerModelName}s, ${modelName}Response.class);
        return responses;
    }

    @Override
    public Page<${modelName}Response> getPage(${modelName}Request request) {
        Page<${modelName}> page = null;
        return convert(page, ${modelName}Response.class);
    }
}

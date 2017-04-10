package ${servicePackageName};

import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.IBaseService;
import ${entityPackageName}.${modelName};
import ${modelPackageName}.request.${modelName}Request;
import ${modelPackageName}.response.${modelName}Response;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${modelName} Service接口
 * @create ${.now}
 */
public interface I${modelName}Service extends IBaseService<${modelName}, ${pkType}> {

    @Transactional
    public ${modelName}Response save(${modelName}Request request);

    @Transactional
    public ${modelName}Response update(${modelName}Request request);

    @Transactional
    public ${pkType} del(${pkType} id);

    public ${modelName}Response get(${pkType} id);

    public List<${modelName}Response> get${modelName}s(${modelName}Request request);

    public Page<${modelName}Response> getPage(${modelName}Request request);
}

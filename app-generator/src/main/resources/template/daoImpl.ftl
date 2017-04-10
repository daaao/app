package ${daoImplPackageName};

import io.zhijian.base.dao.impl.BaseDao;
import ${daoPackageName}.I${modelName}Dao;
import ${entityPackageName}.${modelName};
import org.springframework.stereotype.Repository;

/**
 * ${modelName} DAO实现
 * @create ${.now}
 */
@Repository
public class ${modelName}Dao extends BaseDao<${modelName}, ${pkType}> implements I${modelName}Dao {
}

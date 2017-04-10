package io.zhijian.system.service.impl;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.exception.ApplicationException;
import io.zhijian.base.exception.StatusCode;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.BaseService;
import io.zhijian.log.annotation.Log;
import io.zhijian.system.dao.IDictionaryDao;
import io.zhijian.system.dao.IDictionaryTypeDao;
import io.zhijian.system.entity.DictionaryType;
import io.zhijian.system.model.request.DictionaryTypeRequest;
import io.zhijian.system.model.response.DictionaryResponse;
import io.zhijian.system.model.response.DictionaryTypeResponse;
import io.zhijian.system.service.IDictionaryService;
import io.zhijian.system.service.IDictionaryTypeService;
import io.zhijian.utils.BeanCopier;
import io.zhijian.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * DictionaryType Service实现类
 * @create 2017-4-3 17:45:00
 */
@Service
public class DictionaryTypeService extends BaseService<DictionaryType, Integer> implements IDictionaryTypeService {

    @Autowired
    private IDictionaryTypeDao dictionaryTypeDao;

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Autowired
    private IDictionaryService dictionaryService;

    @Override
    public IBaseDao<DictionaryType, Integer> getEntityDao() {
        return dictionaryTypeDao;
    }

    @Override
    @Transactional
    @Log(module = "系统字典类型", description = "添加字典类型")
    public DictionaryTypeResponse save(DictionaryTypeRequest request) {
        DictionaryType existing = dictionaryTypeDao.find("code", request.getCode());//编码不能一致
        if (existing == null) {//判断是否已存在
            DictionaryType dictionarytype = BeanCopier.copy(request, DictionaryType.class);

            save(dictionarytype);

            return BeanCopier.copy(dictionarytype, DictionaryTypeResponse.class);
        } else {
            //数据已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统字典类型", description = "修改字典类型")
    public DictionaryTypeResponse update(DictionaryTypeRequest request) {
        DictionaryType existing = dictionaryTypeDao.get(request.getId());
        if (existing != null) {
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            existing.setUpdateTime(new Date());

            dictionaryTypeDao.update(existing);

            return BeanCopier.copy(existing, DictionaryTypeResponse.class);
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统字典类型", description = "删除字典类型")
    public Integer del(Integer id) {
        DictionaryType existing = dictionaryTypeDao.find(id);
        if(existing!=null){
            List<DictionaryResponse> dicts = dictionaryService.getDictionarys(existing.getCode());
            if(!Utils.isEmpty(dicts)){
                throw new ApplicationException(StatusCode.FAILED_DEPENDENCY.getCode(), StatusCode.FAILED_DEPENDENCY.getMessage());
            }

            dictionaryTypeDao.delete(id);
            return 1;
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public DictionaryTypeResponse get(Integer id) {
        DictionaryType existing = dictionaryTypeDao.get(id);
        if(existing!=null){
            return BeanCopier.copy(existing, DictionaryTypeResponse.class);
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public List<DictionaryTypeResponse> getDictionaryTypes(DictionaryTypeRequest request) {
        List<DictionaryType> dictionarytypes = null;
        List<DictionaryTypeResponse> responses = BeanCopier.copy(dictionarytypes, DictionaryTypeResponse.class);
        return responses;
    }

    @Override
    public List<DictionaryTypeResponse> getDictionaryTypes() {
        List<DictionaryType> dictionarytypes = dictionaryTypeDao.findAll();
        List<DictionaryTypeResponse> responses = BeanCopier.copy(dictionarytypes, DictionaryTypeResponse.class);
        return responses;
    }

    @Override
    public Page<DictionaryTypeResponse> getPage(DictionaryTypeRequest request) {
        Page<DictionaryType> page = null;
        return convert(page, DictionaryTypeResponse.class);
    }
}

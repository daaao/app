package io.zhijian.system.service.impl;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.exception.ApplicationException;
import io.zhijian.base.exception.StatusCode;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.BaseService;
import io.zhijian.log.annotation.Log;
import io.zhijian.system.dao.IDictionaryDao;
import io.zhijian.system.entity.Dictionary;
import io.zhijian.system.entity.DictionaryType;
import io.zhijian.system.model.request.DictionaryRequest;
import io.zhijian.system.model.response.DictionaryResponse;
import io.zhijian.system.service.IDictionaryService;
import io.zhijian.utils.BeanCopier;
import io.zhijian.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Dictionary Service实现类
 * @create 2017-4-3 17:45:00
 */
@Service
public class DictionaryService extends BaseService<Dictionary, Integer> implements IDictionaryService {

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Override
    public IBaseDao<Dictionary, Integer> getEntityDao() {
        return dictionaryDao;
    }

    @Override
    @Transactional
    @Log(module = "系统字典数据", description = "添加字典数据")
    public DictionaryResponse save(DictionaryRequest request) {
        Dictionary existing = dictionaryDao.findByTypeAndCode(request.getType(), request.getCode());
        if (existing == null) {//判断是否已存在
            Dictionary dictionary = BeanCopier.copy(request, Dictionary.class);

            if (!Utils.isEmpty(request.getParentId())) {  //没有上级则为顶级
                dictionary.setParent(new Dictionary(request.getParentId()));
            }

            if (!Utils.isEmpty(request.getType())) { //字典类型
                dictionary.setType(new DictionaryType(request.getType()));
            }

            save(dictionary);

            return BeanCopier.copy(dictionary, DictionaryResponse.class);
        } else {
            //数据已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统字典数据", description = "修改字典数据")
    public DictionaryResponse update(DictionaryRequest request) {
        Dictionary existing = dictionaryDao.findByTypeAndCode(request.getType(), request.getCode());
        if (existing != null) {
            /*Dictionary target = dictionaryDao.findByTypeAndCode(request.getType(), request.getCode());
            if (target != null && target.getId() != existing.getId()) {
                //数据已存在
                throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
            }*/
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            existing.setSequence(request.getSequence());
            existing.setUpdateTime(new Date());

            if (!Utils.isEmpty(request.getParentId())) {//没有上级则为顶级
                existing.setParent(new Dictionary(request.getParentId()));
            } else {
                existing.setParent(null);
            }

            dictionaryDao.update(existing);

            return BeanCopier.copy(existing, DictionaryResponse.class);
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统字典数据", description = "删除字典数据")
    public Integer del(Integer id) {
        Dictionary existing = dictionaryDao.find(id);
        if(existing!=null){
            dictionaryDao.delete(id);
            return 1;
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public DictionaryResponse get(Integer id) {
        Dictionary existing = dictionaryDao.get(id);
        if(existing!=null){
            return BeanCopier.copy(existing, DictionaryResponse.class);
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public List<DictionaryResponse> getDictionarys(DictionaryRequest request) {
        List<Dictionary> dictionarys = null;
        List<DictionaryResponse> responses = BeanCopier.copy(dictionarys, DictionaryResponse.class);
        return responses;
    }

    @Override
    public List<DictionaryResponse> getDictionarys(String type) {
        List<Dictionary> dictionarys = findByProperty("type.code", type);
        List<DictionaryResponse> responses = BeanCopier.copy(dictionarys, DictionaryResponse.class);
        sort(responses);
        return responses;
    }

    @Override
    public Page<DictionaryResponse> getPage(DictionaryRequest request) {
        Page<Dictionary> page = null;
        return convert(page, DictionaryResponse.class);
    }

    /**
     * 排序
     *
     * @param list
     */
    private void sort(List<DictionaryResponse> list) {
        if (!Utils.isEmpty(list)) {
            Collections.sort(list, new Comparator<DictionaryResponse>() {
                public int compare(DictionaryResponse arg0, DictionaryResponse arg1) {
                    if (arg0 != null && arg1 != null && arg0.getSequence() != null && arg1.getSequence() != null) {
                        return arg0.getSequence().compareTo(arg1.getSequence());
                    } else {
                        return 0;
                    }
                }
            });
        }
    }
}

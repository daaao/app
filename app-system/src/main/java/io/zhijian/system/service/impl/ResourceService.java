package io.zhijian.system.service.impl;

import io.zhijian.base.dao.IBaseDao;
import io.zhijian.base.exception.ApplicationException;
import io.zhijian.base.exception.StatusCode;
import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.BaseService;
import io.zhijian.log.annotation.Log;
import io.zhijian.system.dao.IResourceDao;
import io.zhijian.system.entity.Resource;
import io.zhijian.system.model.request.ResourceRequest;
import io.zhijian.system.model.response.ResourceResponse;
import io.zhijian.system.service.IResourceService;
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
 * Resource Service实现类
 * @create 2017-3-28 10:45:58
 */
@Service
public class ResourceService extends BaseService<Resource, Integer> implements IResourceService {

    @Autowired
    private IResourceDao resourceDao;

    @Override
    public IBaseDao<Resource, Integer> getEntityDao() {
        return resourceDao;
    }

    @Override
    @Transactional
    @Log(module = "系统资源", description = "添加资源信息")
    public ResourceResponse save(ResourceRequest request) {
        Resource existing = resourceDao.find("code", request.getCode());//权限编码不能一致
        if (existing == null) {//判断是否已存在
            Resource resource = BeanCopier.copy(request, Resource.class);

            if (!Utils.isEmpty(request.getParentId())) {  //没有上级则为顶级
                resource.setParent(new Resource(request.getParentId()));
            }

            save(resource);

            return BeanCopier.copy(resource, ResourceResponse.class);
        } else {
            //数据已存在
            throw new ApplicationException(StatusCode.CONFLICT.getCode(), StatusCode.CONFLICT.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统资源", description = "修改资源信息")
    public ResourceResponse update(ResourceRequest request) {
        Resource existing = resourceDao.get(request.getId());
        if (existing != null) {
            //设置要更新的字段
            existing.setName(request.getName());
            existing.setUrl(request.getUrl());
            existing.setCode(request.getCode());
            existing.setIcon(request.getIcon());
            existing.setDescription(request.getDescription());
            existing.setUpdateTime(new Date());
            existing.setSequence(request.getSequence());

            if (!Utils.isEmpty(request.getParentId())) {//没有上级则为顶级
                existing.setParent(new Resource(request.getParentId()));
            } else {
                existing.setParent(null);
            }

            resourceDao.update(existing);

            return BeanCopier.copy(existing, ResourceResponse.class);
        } else {
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    @Transactional
    @Log(module = "系统资源", description = "删除资源信息")
    public Integer del(Integer id) {
        Resource existing = resourceDao.find(id);
        if(existing!=null){
            resourceDao.delete(id);
            return 1;
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public ResourceResponse get(Integer id) {
        Resource existing = resourceDao.get(id);
        if(existing!=null){
            return BeanCopier.copy(existing, ResourceResponse.class);
        }else{
            //数据不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
        }
    }

    @Override
    public List<ResourceResponse> getResources(ResourceRequest request) {
        List<Resource> resources = null;
        List<ResourceResponse> responses = BeanCopier.copy(resources, ResourceResponse.class);
        return responses;
    }

    @Override
    public List<ResourceResponse> getResources() {
        List<Resource> resources = findAll();
        List<ResourceResponse> responses = BeanCopier.copy(resources, ResourceResponse.class);
        sort(responses);
        return responses;
    }

    @Override
    public Page<ResourceResponse> getPage(ResourceRequest request) {
        Page<Resource> page = null;
        return convert(page, ResourceResponse.class);
    }

    @Override
    public List<ResourceResponse> getResources(Integer roleId) {
        List<Resource> resources = resourceDao.findResource(roleId);
        List<ResourceResponse> responses = BeanCopier.copy(resources, ResourceResponse.class);
        sort(responses);
        return responses;
    }

    @Override
    public List<ResourceResponse> getResources(Integer parentId, String type) {
        List<Resource> resources = resourceDao.findResource(parentId, type);
        List<ResourceResponse> responses = BeanCopier.copy(resources, ResourceResponse.class);
        sort(responses);
        return responses;
    }

    @Override
    public List<ResourceResponse> getResourceByUser(String username, String type) {
        List<Resource> resources = resourceDao.findResourceByUser(username, type);
        List<ResourceResponse> responses = BeanCopier.copy(resources, ResourceResponse.class);
        sort(responses);
        return responses;
    }

    /**
     * 排序
     *
     * @param list
     */
    private void sort(List<ResourceResponse> list) {
        if (!Utils.isEmpty(list)) {
            Collections.sort(list, new Comparator<ResourceResponse>() {
                public int compare(ResourceResponse arg0, ResourceResponse arg1) {
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

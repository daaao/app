package io.zhijian.system.service;

import io.zhijian.base.model.response.Page;
import io.zhijian.base.service.IBaseService;
import io.zhijian.system.entity.Resource;
import io.zhijian.system.model.request.ResourceRequest;
import io.zhijian.system.model.response.ResourceResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Resource Service接口
 * @create 2017-3-28 10:45:58
 */
public interface IResourceService extends IBaseService<Resource, Integer> {

    @Transactional
    public ResourceResponse save(ResourceRequest request);

    @Transactional
    public ResourceResponse update(ResourceRequest request);

    @Transactional
    public Integer del(Integer id);

    public ResourceResponse get(Integer id);

    public List<ResourceResponse> getResources(ResourceRequest request);

    public List<ResourceResponse> getResources();

    public Page<ResourceResponse> getPage(ResourceRequest request);

    public List<ResourceResponse> getResources(Integer roleId);

    public List<ResourceResponse> getResources(Integer parentId, String type);

    public List<ResourceResponse> getResourceByUser(String username, String type);
}

package io.zhijian.system.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.zhijian.base.model.response.BaseResponse;

public class ResourceResponse extends BaseResponse {

    /*** 权限编码     */
    private String code;

    /*** 名称     */
    private String name;

    /*** 父级     */
    private ResourceResponse parent;
    @JSONField(name="_parentId")
    private Integer _parentId;//easyui tree的父级节点

    /*** 类型     */
    private String type;

    /*** Url路径     */
    private String url;

    /*** 菜单按钮     */
    private String icon;

    /*** 描述     */
    private String description;

    /*** 排序     */
    private Integer sequence;

    /**
     * 是否固定， 0默认为不固定，1=固定；固定就不能再去修改了
     */
    private Integer isFixed;

    /**
     * 状态：0=启用，1=禁用
     */
    private Integer status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceResponse getParent() {
        return parent;
    }

    public void setParent(ResourceResponse parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Integer isFixed) {
        this.isFixed = isFixed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer get_parentId() {
        if (parent == null) {
            return null;
        } else {
            return parent.getId();
        }
    }

    public void set_parentId(Integer _parentId) {
        this._parentId = _parentId;
    }
}

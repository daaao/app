package io.zhijian.system.model.request;

import io.zhijian.base.model.request.BaseRequest;

public class ResourceRequest extends BaseRequest {

    /*** 权限编码     */
    private String code;

    /*** 名称     */
    private String name;

    /*** 父级ID     */
    private Integer parentId;

    /*** 类型，1=菜单；2=按钮     */
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

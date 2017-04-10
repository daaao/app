package io.zhijian.system.model.request;

import io.zhijian.base.model.request.BaseRequest;

public class RoleRequest extends BaseRequest {

    /*** 角色编码     */
    private String code;

    /*** 角色名称     */
    private String name;

    /*** 描述     */
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

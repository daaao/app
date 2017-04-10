package io.zhijian.system.model.response;

import io.zhijian.base.model.response.BaseResponse;

/**
 * Dictionary响应体
 *
 * @create 2017-4-3 17:45:00
 */
public class DictionaryResponse extends BaseResponse {

    /*** 字典编码     */
    private String code;

    /*** 字典名称     */
    private String name;

    /*** 字典名称     */
    private DictionaryResponse parent;

    /*** 字典类型     */
    private DictionaryTypeResponse type;

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

    public DictionaryResponse getParent() {
        return parent;
    }

    public void setParent(DictionaryResponse parent) {
        this.parent = parent;
    }

    public DictionaryTypeResponse getType() {
        return type;
    }

    public void setType(DictionaryTypeResponse type) {
        this.type = type;
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
}

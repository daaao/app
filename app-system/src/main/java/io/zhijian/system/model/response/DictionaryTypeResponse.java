package io.zhijian.system.model.response;

import io.zhijian.base.model.response.BaseResponse;

/**
 * DictionaryType响应体
 *
 * @create 2017-4-3 17:45:00
 */
public class DictionaryTypeResponse extends BaseResponse {

    /*** 字典类型编码     */
    private String code;

    /*** 字典类型名称     */
    private String name;

    /*** 描述     */
    private String description;

    /**
     * 是否固定， 0默认为不固定，1=固定；固定就不能再去修改了
     */
    private Integer isFixed;

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

    public Integer getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Integer isFixed) {
        this.isFixed = isFixed;
    }
}

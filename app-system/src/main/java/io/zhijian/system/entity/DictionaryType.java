package io.zhijian.system.entity;

import io.zhijian.base.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author Hao
 * @create 2017-04-03
 */
@Entity(name = "sys_dictionary_type")
@DynamicUpdate
@DynamicInsert
public class DictionaryType extends BaseEntity implements Serializable{

    /*** 字典类型编码     */
    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    /*** 字典类型名称     */
    @Column(name = "name", length = 100)
    private String name;

    /*** 描述     */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * 是否固定， 0默认为不固定，1=固定；固定就不能再去修改了
     */
    @Column(name = "is_fixed", precision = 1, columnDefinition = "int default 0")
    private Integer isFixed;

    public DictionaryType() {
    }

    public DictionaryType(Integer id) {
        super(id);
    }

    public DictionaryType(String code) {
        this.code = code;
    }

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

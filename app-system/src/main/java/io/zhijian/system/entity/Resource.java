package io.zhijian.system.entity;

import io.zhijian.base.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "sys_resource")
@DynamicUpdate
@DynamicInsert
public class Resource extends BaseEntity  implements Serializable {

    /*** 权限编码     */
    @Column(name = "code", unique = true, length = 1000)
    private String code;

    /*** 名称     */
    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Resource parent;

    /*** 类型     */
    @Column(name = "type", length = 100)
    private String type;

    /*** Url路径     */
    @Column(name = "url", length = 2000)
    private String url;

    /*** 菜单按钮     */
    @Column(name = "icon", length = 100)
    private String icon;

    /*** 描述     */
    @Column(name = "description", length = 500)
    private String description;

    /*** 排序     */
    @Column(name = "sequence", precision = 1, columnDefinition = "int default 0")
    private Integer sequence;

    /**
     * 是否固定， 0默认为不固定，1=固定；固定就不能再去修改了
     */
    @Column(name = "is_fixed", precision = 1, columnDefinition = "int default 0")
    private Integer isFixed;

    /**
     * 状态：0=启用，1=禁用
     */
    @Column(name = "status", precision = 1, columnDefinition = "int default 0")
    private Integer status;

    public Resource() {
    }

    public Resource(Integer id) {
        super(id);
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

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
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
}

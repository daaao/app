package io.zhijian.system.entity;

import io.zhijian.base.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "sys_role")
@DynamicUpdate
@DynamicInsert
public class Role extends BaseEntity  implements Serializable {

    /*** 角色编码     */
    @Column(name = "code", nullable = false, unique = true, length = 100)
    private String code;

    /*** 角色名称     */
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

    /**
     * 状态：0=启用，1=禁用
     */
    @Column(name = "status", precision = 1, columnDefinition = "int default 0")
    private Integer status;

    /**
     * 角色拥有的资源
     */
    @ManyToMany(targetEntity = Resource.class, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_resource",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    @Where(clause = "is_deleted=0")
    private Set<Resource> resources = new HashSet<>();

    /**
     * 角色下的用户
     */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(Integer id) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

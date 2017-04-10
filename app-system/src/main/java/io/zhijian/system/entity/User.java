package io.zhijian.system.entity;

import io.zhijian.base.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "sys_user")
@DynamicUpdate
@DynamicInsert
public class User extends BaseEntity  implements Serializable {

    /*** 用户名、登录名     */
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    /*** 密码     */
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    /*** 姓名     */
    @Column(name = "name", length = 200)
    private String name;

    /*** 邮箱     */
    @Column(name = "email", length = 50)
    private String email;

    /*** 手机号     */
    @Column(name = "mobile", length = 40)
    private String mobile;

    /**
     * 性别：0=男，1=女
     */
    @Column(name = "gender", precision = 1, columnDefinition = "int default 0")
    private Integer gender;

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
     * 用户拥有的角色
     * <p>
     * 1、定义该User实体所有关联的Role实体
     * 2、映射连接表为sys_user_role
     * 3、定义连接表中名为user_id的外键列，该外键列参照当前实体对应表的主键列
     * 4、定义连接表中名为role_id的外键列，该外键列参照当前实体的关联实体对应表的主键列
     */
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Where(clause = "is_deleted=0")
    @OrderBy("create_time")
    private Set<Role> roles = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

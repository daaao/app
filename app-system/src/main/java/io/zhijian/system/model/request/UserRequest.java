package io.zhijian.system.model.request;

import io.zhijian.base.model.request.BaseRequest;
import org.hibernate.validator.constraints.NotEmpty;

public class UserRequest extends BaseRequest {

    /*** 用户名、登录名     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /*** 密码     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /*** 姓名     */
    private String name;

    /*** 邮箱     */
    private String email;

    /*** 手机号     */
    private String mobile;

    /**
     * 性别：0=男，1=女
     */
    private Integer gender;

    /**
     * 状态：0=启用，1=禁用
     */
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

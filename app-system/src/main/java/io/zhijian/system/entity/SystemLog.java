package io.zhijian.system.entity;

import io.zhijian.base.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "sys_log")
@DynamicUpdate
@DynamicInsert
public class SystemLog extends BaseEntity  implements Serializable {

    /*** 用户名、登录名     */
    @Column(name = "username", length = 100)
    private String username;

    /*** 模块     */
    @Column(name = "module", length = 200)
    private String module;

    /*** 描述     */
    @Column(name = "description", length = 500)
    private String description;

    /*** 响应时间     */
    @Column(name = "response_time", length = 100)
    private String responseTime;

    /*** IP     */
    @Column(name = "ip", length = 100)
    private String ip;

    /*** 内容     */
    @Column(name = "content", length = 2000)
    private String content;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package io.zhijian.system.model.response;

import io.zhijian.base.model.response.BaseResponse;

/**
* SystemLog响应体
* @create 2017-3-29 21:34:13
*/
public class SystemLogResponse extends BaseResponse {

    /*** 用户名、登录名     */
    private String username;

    /*** 模块     */
    private String module;

    /*** 描述     */
    private String description;

    /*** 响应时间     */
    private String responseTime;

    /*** IP     */
    private String ip;

    /*** 内容     */
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

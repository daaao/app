package io.zhijian.base.model.response;

import java.util.Date;

/**
 * 响应封装
 */
public class Response {

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态描述信息
     */
    private String message;

    /**
     * 响应主体
     */
    private Object body;

    /**
     * 服务器时间
     */
    private Date now;

    public Response() {
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(int code, String message, Object body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Date getNow() {
        return new Date();
    }

    public void setNow(Date now) {
        this.now = now;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", body=" + body +
                ", now=" + now +
                '}';
    }
}

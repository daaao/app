package io.zhijian.base.exception;

/**
 * Created by Hao on 2017-03-23.
 */
public enum StatusCode {

    /*
        错误码格式说明（示例：202001）
        --------------------------------------------------------------------
        服务级错误（1为系统级错误）	服务模块代码(即业务模块标识)	具体错误代码
                2                            02	                    001
        --------------------------------------------------------------------
    */

    /*常用的状态码，业务模块的状态码请勿添加在此处，而应该是在各个业务模块定义，格式如上*/
    OK(200, "ok"),//成功
    SERVER_ERROR(500, "internal server error"),//服务器内部错误
    BAD_REQUEST(400, "bad request"),//请求格式错误、参数错误
    UNAUTHORIZED(401, "unauthorized"),//未授权
    NOT_FOUND(404, "not found"),//请求的资源不存在、数据不存在
    CONFLICT(409, "conflict"),//资源存在冲突、数据已存在
    FAILED_DEPENDENCY(424, "failed dependency"),//存在数据依赖
    ;

    /*状态码*/
    private int code;
    /*信息*/
    private String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

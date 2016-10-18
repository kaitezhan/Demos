package com.boneix.base.domain;



/**
 * 错误返回代码
 * 
 * @author boneix
 * @version [0.1, 2016年4月19日]
 */
public enum CommonError {
    SUCCESS(1010000, "成功"),

    QUERY_ERROR(1010001, "查询操作错误"),

    INSERT_ERROR(1010002, "插入操作错误"),

    UPDATE_ERROR(1010003, "更新操作错误"),

    DELETE_ERROR(1010004, "删除操作错误"),

    DELETE_IN_LOGICAL_ERROR(1010005, "逻辑删除操作错误"),

    PARAM_ERROR(1010005, "参数错误"),

    OUTTER_ERROR(1010006, "依赖接口错误或超时"),

    USER_NOT_EXIST_ERROR(1020001, "用户不存在"),

    USER_EXIST_ERROR(1020002, "用户已存在"),

    ACCOUNT_PASSWORD_ERROR(1020003, "账户或密码错误"),

    REDIS_TOKEN_ERROR(1020004, "Token已失效"),

    USERNAME_EXIST_ERROR(1020005, "用户名已存在"),

    EMAIL_EXIST_ERROR(1020006, "邮箱已存在"),

    EMAIL_NOT_EXIST_ERROR(1020008, "邮箱不存在"),

    SESSION_OUT_TIME(1020007, "Session已失效"),

    PASSWORD_ERROR(1020003, "密码错误"),

    REDIS_ERROR(1010007, "redis存取异常"),

    INTER_ERROR(1010500, "服务器内部错误"),

    ;

    // 错误码
    private Integer errorCode;

    // 提示信息
    private String msg;

    /**
     * 创建一个新的实例 CommonError.
     * 
     * @param errorCode
     * @param msg
     */
    CommonError(Integer errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    /**
     * errorCode
     * 
     * @return the errorCode
     */

    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * msg
     * 
     * @return the msg
     */

    public String getMsg() {
        return msg;
    }

    /**
     * 设置返回体
     * 
     * @param responseVo ResponseVo
     * @param commonError CommonError
     */
    public static void setErrorCode(ResponseVo responseVo, CommonError commonError) {
        if (0 == commonError.compareTo(CommonError.SUCCESS)) {
            responseVo.setSuccess(true);
        } else {
            responseVo.setSuccess(false);
        }
        responseVo.setMsg(commonError.getMsg());
        responseVo.setErrorCode(commonError.getErrorCode());
    }

}

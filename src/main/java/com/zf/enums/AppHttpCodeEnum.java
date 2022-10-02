package com.zf.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    FAIL(201, "操作失败"),
    // 登录
    COMPANY_NOF_FIND(405,"该公司不存在"),
    PARAMETER_ERROR(400,"参数异常"),

    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    ROLE_EXIST(500,"该角色已存在!"),
    ROLE_NOT_EXIST(500,"该角色不存在!"),

    DEPARTMENT_NOT_EXIST(500,"该部门不存在!"),
    DEPARTMENT_EXIST(500,"该部门已存在!"),

    SYSTEM_ERROR(500, "出现错误"),

    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_INVALID(401, "token非法,重新登录"),
    LOGIN_ERROR(505, "用户名或密码错误");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

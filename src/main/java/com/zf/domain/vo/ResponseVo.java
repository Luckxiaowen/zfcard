package com.zf.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zf.enums.AppHttpCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "响应的VO对象",description = "封装接口返回给前端的数据")
public class ResponseVo<T> implements Serializable {

    @ApiModelProperty(value = "响应状态码",dataType = "int")
    private Integer code;
    @ApiModelProperty(value = "响应提示信息",dataType = "string")
    private String msg;
    @ApiModelProperty(value = "响应数据",dataType = "object")
    private T data;

    public ResponseVo() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    public ResponseVo(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseVo errorResult(int code, String msg) {
        ResponseVo result = new ResponseVo();
        return result.error(code, msg);
    }

    public static ResponseVo okResult() {
        ResponseVo result = new ResponseVo();
        return result;
    }

    public static ResponseVo okResult(int code, String msg) {
        ResponseVo result = new ResponseVo();
        return result.ok(code, null, msg);
    }

    public static ResponseVo okResult(Object data) {
        ResponseVo result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, AppHttpCodeEnum.SUCCESS.getMsg());
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static ResponseVo errorResult(AppHttpCodeEnum enums) {
        return setAppHttpCodeEnum(enums, enums.getMsg());
    }

    public static ResponseVo errorResult(AppHttpCodeEnum enums, String msg) {
        return setAppHttpCodeEnum(enums, msg);
    }

    public static ResponseVo setAppHttpCodeEnum(AppHttpCodeEnum enums) {
        return okResult(enums.getCode(), enums.getMsg());
    }

    private static ResponseVo setAppHttpCodeEnum(AppHttpCodeEnum enums, String msg) {
        return okResult(enums.getCode(), msg);
    }

    public ResponseVo<?> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseVo<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseVo<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public ResponseVo<?> ok(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
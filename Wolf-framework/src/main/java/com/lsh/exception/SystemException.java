package com.lsh.exception;

import com.lsh.enums.AppHttpCodeEnum;

/**
 * 自定义异常处理器
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        //最后输出的内容
        super(httpCodeEnum.getMsg());

        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}

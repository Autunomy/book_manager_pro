package com.hty.bm.constant;

public enum ResponseMessage {

    SUCCESS(6666,"成功"),
    ERROR(8888,"出错了"),
    USER_NOT_FOUND(7777,"用户未找到"),
    PASSWORD_NOT_MATCHED(9999,"密码错误");

    public final Integer code;
    public final String msg;

    ResponseMessage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

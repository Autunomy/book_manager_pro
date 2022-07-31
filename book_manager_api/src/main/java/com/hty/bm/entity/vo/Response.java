package com.hty.bm.entity.vo;

import com.hty.bm.constant.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Integer code;
    private String msg;
    private Object data;

    public Response(ResponseMessage responseMessage) {
        this.msg = responseMessage.msg;
        this.code = responseMessage.code;
    }

    public Response data(Object data){
        this.data = data;
        return this;
    }
}

package com.hty.bm.entity.vo;

import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.entity.PageInfo;
import lombok.Data;

@Data
public class PageResponse extends Response{
    private PageInfo pageInfo;

    public PageResponse(ResponseMessage responseMessage) {
        super(responseMessage);
    }

    public PageResponse pageInfo(PageInfo pageInfo){
        this.pageInfo = pageInfo;
        return this;
    }

}

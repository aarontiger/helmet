package com.juxingtech.helmet.common.result;

import lombok.Data;

/**
 * @author haoxr
 * @date 2020-06-24
 **/
@Data
public class PageResult<T> extends Result {

    private long total;

    public static <T> PageResult<T> success(T data, Long total) {
        PageResult<T> pageResult = new PageResult();
        pageResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        pageResult.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        pageResult.setData(data);
        pageResult.setTotal(total);
        return pageResult;
    }

}

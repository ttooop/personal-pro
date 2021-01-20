package com.example.personalproservice.entity;

import lombok.Data;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午6:08 2021/1/20
 */

@Data
public class Result {
    private String status;

    private Object data;

    public static Result create(Object result) {
        return Result.create(result, "success");
    }

    public static Result create(Object result, String status) {
        Result type = new Result();
        type.setData(result);
        type.setStatus(status);
        return type;
    }
}

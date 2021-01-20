package com.example.personalproservice.service;

import com.example.personalproservice.entity.Result;

import java.io.IOException;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午7:21 2021/1/20
 */

public interface ESQueryService {
    Result querydemo(String query) throws IOException;
}

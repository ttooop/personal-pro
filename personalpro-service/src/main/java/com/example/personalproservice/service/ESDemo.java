package com.example.personalproservice.service;

import java.io.IOException;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午1:30 2021/1/20
 */

public interface ESDemo {

    boolean createIndex(String indexname) throws IOException;

    boolean deleteIndex(String indexname) throws IOException;

    String addData(Object obj, String indexname) throws IOException;

    void addAlias(String indexname, String aliasname) throws IOException;
}

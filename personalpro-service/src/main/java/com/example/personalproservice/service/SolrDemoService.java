package com.example.personalproservice.service;

import com.example.personalproservice.entity.Result;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午8:50 2021/2/3
 */

public interface SolrDemoService {
    Result delProductByQuery(String query) throws IOException, SolrServerException;

    Result addDocument() throws IOException, SolrServerException;

    Result updateDocById(Long id) throws IOException, SolrServerException;
}

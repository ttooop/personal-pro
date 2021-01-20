package com.example.personalproservice.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.personalproservice.service.ESDemo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午1:30 2021/1/20
 */

@Service
@Slf4j
public class ESDemoImpl implements ESDemo {
    @Qualifier("restHighLevelClient")
    @Autowired
    private RestHighLevelClient client;

    /**
     * 创建索引
     *
     * @param indexname
     * @return
     * @throws IOException
     */
    @Override
    public boolean createIndex(String indexname) throws IOException {
        if (isExistingIndex(indexname)) {
            log.error("Index has been created!");
            return false;
        }

        //创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(indexname);
        //ES创建索引后返回内容
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    private boolean isExistingIndex(String indexname) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexname);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @param indexname
     * @return
     * @throws IOException
     */
    @Override
    public boolean deleteIndex(String indexname) throws IOException {
        if (!isExistingIndex(indexname)) {
            log.error("Sorry! the index is not exit");
            return false;
        }
        //删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest(indexname);
        //ES返回内容
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public String addData(Object obj, String indexname) throws IOException {
        IndexRequest request = new IndexRequest(indexname);

        request.source(JSON.toJSONString(obj), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return response.getId();
    }


}

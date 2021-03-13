package com.example.personalproservice.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.personalproservice.entity.Result;
import com.example.personalproservice.service.SolrDemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午8:50 2021/2/3
 */
@Service
@Slf4j
public class SolrDemoServiceImpl implements SolrDemoService {

    @Resource
    private SolrClient solrClient;

    private String SOLR_CORE = "new_core";

    /**
     *根据query条件进行删除
     * @param query like "name:aaa"
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Override
    public Result delProductByQuery(String query) throws IOException, SolrServerException {
        UpdateResponse updateResponse = solrClient.deleteByQuery(SOLR_CORE, query);
        solrClient.commit(SOLR_CORE);
        log.info("response status is: {}",updateResponse.getStatus());
        return Result.create(updateResponse.getStatus());
    }

    /**
     * 增加文档数据
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Override
    public Result addDocument() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 1);
        document.addField("name", "test11");

        UpdateResponse updateResponse = solrClient.add(SOLR_CORE, document);
        solrClient.commit(SOLR_CORE);
        System.out.println(updateResponse.getStatus());
        return Result.create(updateResponse.getStatus());
    }

    /**
     * 根据ID修改数据
     * @param id
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Override
    public Result updateDocById(Long id) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("id:" + id);
        QueryResponse response = solrClient.query(SOLR_CORE, query);
        SolrDocument doc = response.getResults().get(0);
        System.out.println(JSON.toJSONString(doc));
        doc.addField("content", "added content");
        System.out.println("======");
        System.out.println(JSON.toJSONString(doc));
        SolrInputDocument newdoc = convertToInputDoc(doc);
        UpdateResponse updateResponse = solrClient.add(SOLR_CORE, newdoc);
        solrClient.commit(SOLR_CORE);
        return Result.create("success");
    }

    /**
     * 查询数据转换-》input数据
     * @param document
     * @return
     */
    private SolrInputDocument convertToInputDoc(SolrDocument document) {
        SolrInputDocument newdoc = new SolrInputDocument();
        for (String key :
                document.getFieldNames()) {
            if ("standardTitle".equals(key)) {
                newdoc.addField(key, document.getFieldValues(key).toString());
                continue;
            }
            newdoc.addField(key, document.getFieldValue(key));
        }
        return newdoc;
    }
}

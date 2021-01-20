package com.example.personalproservice.service.Impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.example.personalproservice.entity.Result;
import com.example.personalproservice.service.ESQueryService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午7:22 2021/1/20
 */
@Service
@Slf4j
public class ESQueryServiceImpl implements ESQueryService {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    public Result querydemo(String query) throws IOException {
        Assert.isTrue(StrUtil.isNotBlank(query), "搜索词无效");

        Map<String, Object> result = new HashMap<>();

        log.info("query word is {} ", query);

        SearchRequest request = new SearchRequest("index_test");

        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(query);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(queryStringQueryBuilder)
                .from(0)
                .size(5);

        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        log.info("the search result is {} ", response);

        SearchHit[] searchHits = response.getHits().getHits();
        List<Map<String, Object>> resultMap = new ArrayList<>();
        Arrays.stream(searchHits).forEach(s -> {
            Map<String, Object> m = s.getSourceAsMap();
            resultMap.add(m);
        });

        result.put("searchresult", resultMap);

        return Result.create(result);
    }
}

package com.example.personalproservice.controller;

import com.example.personalproservice.entity.Result;
import com.example.personalproservice.service.ESDemo;
import com.example.personalproservice.service.ESQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午1:31 2021/1/20
 */
@RestController
@RequestMapping("/personal")
@Api("personal-pro")
public class ESDemoController {
    @Autowired
    private ESDemo esDemo;

    @Autowired
    private ESQueryService esQueryService;

    @ApiOperation("test")
    @GetMapping("/")
    public Result test() {
        return Result.create("hello test!");
    }

    @ApiOperation("ES-Search-demo")
    @GetMapping("/search")
    @ApiImplicitParam(name = "query", value = "搜索词", paramType = "query", required = true)
    public Result searchDemo(@RequestParam(value = "query", required = true) String query) throws IOException {
        return esQueryService.querydemo(query);
    }
}

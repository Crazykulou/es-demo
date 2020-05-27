package com.crazykulou.controller;

import com.crazykulou.config.ESRestClient;
import com.crazykulou.entity.User;
import com.crazykulou.service.IUserService;
import com.crazykulou.util.ESUtil;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    ESRestClient esClient;

    @RequestMapping("")
    @PostMapping
    @ResponseBody
    public String save(@RequestBody User user) {
        boolean flag = userService.save(user);
        String Id = null;
        String index = "user";
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("id", user.getId())
                    .field("email", user.getEmail())
                    .field("nickName", user.getNickName())
                    .field("userName", user.getUserName())
                    .field("age", user.getAge())
                    .endObject();
            Id = ESUtil.addData(builder, index, String.valueOf(user.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Id;
    }

    @RequestMapping("searchByCondition")
    @GetMapping
    @ResponseBody
    public List searchByCondition(@RequestParam String name, @RequestParam String nickName) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        String[] fields = {"userName", "nickName"};
        sourceBuilder.fetchSource(fields, null);
        WildcardQueryBuilder query1 = QueryBuilders.wildcardQuery("userName", "*" + name + "*");
        WildcardQueryBuilder query2 = QueryBuilders.wildcardQuery("nickName", "*" + nickName + "*");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(query1);
        boolQueryBuilder.should(query2);
        List list = ESUtil.SearchDataPage("user", 1, 10, sourceBuilder, boolQueryBuilder);
        return list;
    }

}

package com.zyc.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhuyc
 * @date 2022/06/25 16:46
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class IndexTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 创建索引库和映射表结构
     * 注意：索引一般不会这么创建
     */
    @Test
    public void indexCreate() throws Exception {
        IndicesClient indicesClient = restHighLevelClient.indices();
        // 创建索引
        CreateIndexRequest indexRequest = new CreateIndexRequest("goods111");
        // 创建表 结构
        String mapping = "{\n" +
                "    \"properties\": {\n" +
                "      \"brandName\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"categoryName\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"createTime\": {\n" +
                "        \"type\": \"date\",\n" +
                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
                "      },\n" +
                "      \"id\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"price\": {\n" +
                "        \"type\": \"double\"\n" +
                "      },\n" +
                "      \"saleNum\": {\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"status\": {\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"stock\": {\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"title\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"ik_max_word\",\n" +
                "        \"search_analyzer\": \"ik_smart\"\n" +
                "      }\n" +
                "    }\n" +
                "  }";
        // 把映射信息添加到request请求里面
        // 第一个参数：表示数据源
        // 第二个参数：表示请求的数据类型
        indexRequest.mapping(mapping, XContentType.JSON);
        // 请求服务器
        CreateIndexResponse response = indicesClient.create(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }


}

package com.zyc.es;

import com.alibaba.fastjson.JSON;
import com.zyc.es.entity.Goods;
import com.zyc.es.mapper.GoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 文档操作
 *
 * @author zhuyc
 * @date 2022/06/25 17:13
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DocumentTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 增加文档信息
     */
    @Test
    public void addDocument() throws IOException {

        // 创建商品信息
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setTitle("Apple iPhone 13 Pro (A2639) 256GB 远峰蓝色 支持移动联通电信5G 双卡双待手机");
        goods.setPrice(new BigDecimal("8799.00"));
        goods.setStock(1000);
        goods.setSaleNum(599);
        goods.setCategoryName("手机");
        goods.setBrandName("Apple");
        goods.setStatus(0);
        goods.setCreateTime(new Date());

        // 将对象转为json
        String data = JSON.toJSONString(goods);
        // 创建索引请求对象
        IndexRequest indexRequest = new IndexRequest("goods").id(goods.getId() + "").source(data, XContentType.JSON);
        // 执行增加文档
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info("创建状态：{}", response.status());
    }

    /**
     * 更新文档信息
     */
    @Test
    public void updateDocument() throws IOException {

        // 设置商品更新信息
        Goods goods = new Goods();
        goods.setTitle("Apple iPhone 13 Pro Max (A2644) 256GB 远峰蓝色 支持移动联通电信5G 双卡双待手机");
        goods.setPrice(new BigDecimal("9999"));

        // 将对象转为json
        String data = JSON.toJSONString(goods);
        // 创建索引请求对象
        UpdateRequest updateRequest = new UpdateRequest("goods", "1");
        // 设置更新文档内容
        updateRequest.doc(data, XContentType.JSON);
        // 执行更新文档
        UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        log.info("创建状态：{}", response.status());
    }



    /**
     * 批量导入测试数据
     */
    @Test
    public void importData() throws IOException {
        //1.查询所有数据，mysql
        List<Goods> goodsList = goodsMapper.findAll();

        //2.bulk导入
        BulkRequest bulkRequest = new BulkRequest();

        //2.1 循环goodsList，创建IndexRequest添加数据
        for (Goods goods : goodsList) {

            //将goods对象转换为json字符串
            String data = JSON.toJSONString(goods);//map --> {}
            IndexRequest indexRequest = new IndexRequest("goods");
            indexRequest.id(goods.getId() + "").source(data, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }


}

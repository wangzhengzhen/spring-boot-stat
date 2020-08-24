package com.engrz.stats.test.elasticsearch;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestElasticsearch {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

//    @Autowired
//    @Qualifier("elasticsearchTemplate")
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 测试连接
     * @throws IOException
     */
    @Test
    public void testConnect() throws IOException {

        RestClient lowLevelClient = restHighLevelClient.getLowLevelClient();
        Request request = new Request("GET", "_cluster/health");
        Response response = lowLevelClient.performRequest(request);
        response.getEntity().writeTo(System.out);
        lowLevelClient.close();
    }

    /**
     * 创建索引
     */
    @Test
    public void testCreateIndices() throws IOException {

        CreateIndexRequest indexRequest = new CreateIndexRequest("id");
        ActionResponse response = restHighLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.toString());

        // 旧方法，不推荐
//        AdminClient adminClient = elasticsearchTemplate.getClient().admin();
//        IndicesAdminClient indicesAdminClient = adminClient.indices();
//
//        ActionResponse response = indicesAdminClient.prepareCreate("id").setSettings(Settings.builder().build()).get();
//        System.out.println(response.toString());
    }

    @Test
    public void testDeleteIndices() throws IOException {

        DeleteIndexRequest indexRequest = new DeleteIndexRequest("id");
        ActionResponse response = restHighLevelClient.indices().delete(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.toString());

        // 旧方法，不推荐
//        AdminClient adminClient = elasticsearchTemplate.getClient().admin();
//        IndicesAdminClient indicesAdminClient = adminClient.indices();
//
//        ActionResponse response = indicesAdminClient.prepareDelete("id").get();
//        System.out.println(response.toString());
    }

}

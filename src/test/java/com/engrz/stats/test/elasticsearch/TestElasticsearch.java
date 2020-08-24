package com.engrz.stats.test.elasticsearch;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestElasticsearch {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private TransportClient transportClient;

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
    public void testCreateIndices() {

        AdminClient adminClient = transportClient.admin();
        IndicesAdminClient indicesAdminClient = adminClient.indices();

        ActionResponse response = indicesAdminClient.prepareCreate("id").setSettings(Settings.builder().build()).get();
        System.out.println(response.toString());
    }

    @Test
    public void testDeleteIndices() {

        AdminClient adminClient = transportClient.admin();
        IndicesAdminClient indicesAdminClient = adminClient.indices();

        ActionResponse response = indicesAdminClient.prepareDelete("id").get();
        System.out.println(response.toString());
    }

}

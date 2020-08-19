package com.engrz.stats.test.elasticsearch;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
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

    @Test
    public void test() throws IOException {

        RestClient lowLevelClient = restHighLevelClient.getLowLevelClient();
        Request request = new Request("GET", "_cluster/health");
        Response response = lowLevelClient.performRequest(request);
        response.getEntity().writeTo(System.out);
        lowLevelClient.close();
    }

}

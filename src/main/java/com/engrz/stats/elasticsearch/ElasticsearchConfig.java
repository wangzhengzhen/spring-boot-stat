package com.engrz.stats.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean("restClient")
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("10.147.20.200:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean("elasticsearchTemplate")
    public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
        TransportClientFactoryBean factoryBean = new TransportClientFactoryBean();
        return new ElasticsearchTemplate(factoryBean.getObject());
    }

    @Bean("esHttpHost")
    public List<HttpHost> esHttpHost() {

        List<HttpHost> esHttphost = new ArrayList<>();
        esHttphost.add(new HttpHost("10.147.20.200", 9200, "http"));

        return esHttphost;
    }

}

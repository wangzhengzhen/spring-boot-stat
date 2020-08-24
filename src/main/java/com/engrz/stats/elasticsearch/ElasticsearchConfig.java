package com.engrz.stats.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.elasticsearch.rest.uris}")
    private String uris;

    @Override
    @Bean("restClient")
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(uris)
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
        String[] split = uris.split(":");
        esHttphost.add(new HttpHost(split[0], Integer.parseInt(split[1]), "http"));

        return esHttphost;
    }

}

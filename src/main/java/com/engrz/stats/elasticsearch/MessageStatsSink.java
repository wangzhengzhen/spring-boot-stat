package com.engrz.stats.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.connectors.elasticsearch.util.RetryRejectedExecutionFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class MessageStatsSink implements Serializable {

    @Autowired
    private MessageStatsSinkFunction sinkFunction;

    @Bean("statsSink")
    public ElasticsearchSink statsSink(@Qualifier("esHttpHost") List<HttpHost> esHttpHost) {

        ElasticsearchSink.Builder esSinkBuilder = new ElasticsearchSink.Builder<>(esHttpHost, sinkFunction);
        esSinkBuilder.setBulkFlushMaxActions(2);
        esSinkBuilder.setBulkFlushInterval(1000L);
        esSinkBuilder.setRestClientFactory(restClientBuilder -> {
            restClientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
                BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("", ""));
                httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                return httpAsyncClientBuilder;
            });
        });
        esSinkBuilder.setFailureHandler(new RetryRejectedExecutionFailureHandler());

        return esSinkBuilder.build();
    }

}

package com.engrz.stats.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MessageStatsSinkFunction implements ElasticsearchSinkFunction<String>, Serializable {

    public IndexRequest createIndexRequest(String element) {

        Map<String, String> json = new HashMap<>();
        json.put("data", element);

        log.info("data -> " + json.toString());

        return Requests.indexRequest()
                .index("stats")
                .source(json);
    }

    @Override
    public void process(String element, RuntimeContext ctx, RequestIndexer indexer) {
        indexer.add(createIndexRequest(element));
    }

}

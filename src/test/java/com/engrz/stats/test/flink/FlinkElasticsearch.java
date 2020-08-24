package com.engrz.stats.test.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class FlinkElasticsearch {

    @Autowired
    private FlinkKafkaConsumer flinkKafkaConsumer;

    @Autowired
    @Qualifier("statsSink")
    private ElasticsearchSink statsSink;

    @Test
    public void stats() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> stream = env.addSource(flinkKafkaConsumer);
        // 如果没有开启checkpoint机制的话，则失败重试策略是无法生效的
        env.enableCheckpointing(5000);
        stream.print();

        stream.addSink(statsSink);

        env.execute();
    }

}

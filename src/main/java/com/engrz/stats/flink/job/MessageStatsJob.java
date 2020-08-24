package com.engrz.stats.flink.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageStatsJob implements Runnable {

    @Autowired
    private FlinkKafkaConsumer flinkKafkaConsumer;

    @Autowired
    @Qualifier("statsSink")
    private ElasticsearchSink statsSink;

    public void run() {

        try {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            DataStream<String> stream = env.addSource(flinkKafkaConsumer);
            env.enableCheckpointing(5000);
            stream.print();

            stream.addSink(statsSink);

            env.execute();

        } catch (Exception e) {
            log.error("异常", e);
        }
    }

}

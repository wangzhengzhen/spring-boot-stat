package com.engrz.stats.test.flink;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple25;
import org.apache.flink.api.java.tuple.builder.Tuple25Builder;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FlinkKafka {

    @Autowired
    private FlinkKafkaConsumer flinkKafkaConsumer;

    /**
     * 消费kafka中的消息
     * @throws Exception
     */
    @Test
    public void kafkaConsumer() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> stream = env.addSource(flinkKafkaConsumer);

        env.enableCheckpointing(5000);
        stream.print();
        env.execute();
    }

}

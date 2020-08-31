package com.engrz.stats.flink;

import com.engrz.stats.kafka.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FlinkKafkaSender {

    @Bean("flinkKafkaProducer")
    public FlinkKafkaProducer<String> kafkaProducer(@Value("${spring.kafka.bootstrap-servers}") String kafkaServers, @Value("${spring.kafka.consumer.group-id}") String groupId) {

        FlinkKafkaProducer<String> kafkaProducer = new FlinkKafkaProducer<>(kafkaServers, KafkaConstants.STATS_TOPIC, new SimpleStringSchema());
        kafkaProducer.setWriteTimestampToKafka(true);
        return kafkaProducer;

    }


}

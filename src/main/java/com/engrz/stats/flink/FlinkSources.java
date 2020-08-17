package com.engrz.stats.flink;

import com.engrz.stats.kafka.KafkaConstants;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class FlinkSources {

    /**
     * kafka消息者
     * @param kafkaServers
     * @param groupId
     * @return
     */
    @Bean
    public FlinkKafkaConsumer<String> kafkaConsumer(@Value("${spring.kafka.bootstrap-servers}") String kafkaServers, @Value("${spring.kafka.consumer.group-id}") String groupId) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaServers);
        properties.setProperty("group.id", groupId);
        FlinkKafkaConsumer<String> myConsumer = new FlinkKafkaConsumer<>(KafkaConstants.STATS_TOPIC, new SimpleStringSchema(), properties);
        return myConsumer;
    }

}

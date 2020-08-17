package com.engrz.stats.kafka;

import com.engrz.stats.model.dto.StatsMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka消息接收，flink代收，这里接收者注释掉
 */
@Slf4j
//@Component
public class KafkaReceiver {

    @KafkaListener(topics = {KafkaConstants.STATS_TOPIC})
    public void listen(String message) {
        try {
            log.info("收到消息：" + message);
            ObjectMapper objectMapper = new ObjectMapper();
            StatsMessageDto dto = objectMapper.readValue(message, StatsMessageDto.class);
            log.info("消息对像：" + dto);
        } catch (JsonProcessingException e) {
            log.error("异常", e);
        }
    }

}

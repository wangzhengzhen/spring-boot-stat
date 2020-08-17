package com.engrz.stats.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaSender<T> {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 发送消息
     * @param obj
     */
    public void send(T obj) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMsg = objectMapper.writeValueAsString(obj);
            log.info("send msg -> " + jsonMsg);
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(KafkaConstants.STATS_TOPIC, jsonMsg);
            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("失败：" + throwable.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                    log.info("成功：" + stringObjectSendResult.toString());
                }
            });

        } catch (JsonProcessingException e) {
            log.error("异常", e);
        }
    }

}

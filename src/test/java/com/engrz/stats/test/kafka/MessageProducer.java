package com.engrz.stats.test.kafka;

import com.engrz.stats.kafka.KafkaSender;
import com.engrz.stats.model.dto.StatsMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * 消息生产者
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageProducer {

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    @Qualifier("flinkKafkaProducer")
    private FlinkKafkaProducer<String> flinkKafkaProducer;

    @Test
    public void producer() {

        StatsMessageDto msg = new StatsMessageDto();
        msg.setId(1L);
        msg.setMsg("test");
        msg.setDate(new Date());
        kafkaSender.send(msg);

    }

    @Test
    public void producerList() {

        for (int i = 0, j = 10; i < j; i++) {
            StatsMessageDto msg = new StatsMessageDto();
            msg.setId((long) i);
            msg.setPlatform(i % 2 == 0 ? "android" : "ios");
            msg.setVersion(1);
            msg.setType(i % 2 == 0 ? "01" : "02");
            msg.setMsg("msg" + i);
            msg.setDate(new Date());
            kafkaSender.send(msg);
        }

    }

    @Test
    public void finkProducer() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        List<String> list = new ArrayList();
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0, j = 10; i < j; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", (long) i);
            map.put("platform", i % 2 == 0 ? "android" : "ios");
            map.put("version", 1);
            map.put("type", i % 2 == 0 ? "01" : "02");
            map.put("content", "msg" + i);
            map.put("date", new Date());
            String msg = objectMapper.writeValueAsString(map);
            list.add(msg);
        }
        DataStream<String> stream = env.fromCollection(list);
        stream.addSink(flinkKafkaProducer);
        env.execute();
    }

}

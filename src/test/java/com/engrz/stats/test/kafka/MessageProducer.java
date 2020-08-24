package com.engrz.stats.test.kafka;

import com.engrz.stats.kafka.KafkaSender;
import com.engrz.stats.model.dto.StatsMessageDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息生产者
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageProducer {

    @Autowired
    private KafkaSender kafkaSender;

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

}

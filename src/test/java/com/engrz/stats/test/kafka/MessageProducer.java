package com.engrz.stats.test.kafka;

import com.engrz.stats.kafka.KafkaSender;
import com.engrz.stats.model.dto.StatsMessageDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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

        StatsMessageDto msg1 = new StatsMessageDto();
        msg1.setId(1L);
        msg1.setMsg("test");
        msg1.setDate(new Date());
        kafkaSender.send(msg1);

    }

}

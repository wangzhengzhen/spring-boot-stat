package com.engrz.test.flink.job;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobMain {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<StatsMessageDto> flintstones = env.fromCollection(genMsgList());
        DataStream<StatsMessageDto> adults = flintstones.filter(dto -> dto.getId() > 2);
        adults.print();
        env.execute();
    }

    /**
     * 产生测试数据
     * @return
     */
    public static List<StatsMessageDto> genMsgList() {

        List<StatsMessageDto> list = new ArrayList<>();
        for (int i = 0, j = 10; i < j; i++) {
            StatsMessageDto msg = new StatsMessageDto();
            msg.setId((long) i);
            msg.setMsg("msg" + i);
            msg.setDate(new Date());
            list.add(msg);
        }
        return list;
    }

}

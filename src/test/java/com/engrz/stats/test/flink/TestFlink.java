package com.engrz.stats.test.flink;

import com.engrz.stats.model.dto.StatsMessageDto;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestFlink {

    /**
     * 先启动 nc -lk 9000
     * @throws Exception
     */
    @Test
    public void windowWordCount() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<String, Integer>> dataStream = env.socketTextStream("localhost", 9000)
                .flatMap(new Splitter())
                .keyBy((KeySelector<Tuple2<String, Integer>, Object>) stringIntegerTuple2 -> 0)
                .timeWindow(Time.seconds(5))
                .sum(1);
        dataStream.print();
        env.execute("Window WordCount");
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word: sentence.split(" ")) {
                out.collect(new Tuple2<>(word, 1));
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void fromCollection() throws Exception {

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
    public List<StatsMessageDto> genMsgList() {

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

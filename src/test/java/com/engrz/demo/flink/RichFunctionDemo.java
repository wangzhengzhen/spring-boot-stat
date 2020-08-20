package com.engrz.demo.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import scala.Tuple6;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RichFunctionDemo {

    /**
     * 分组去重
     */
    @Test
    public void testRichFlatMapFunction() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple6<Long, String, Integer, String, String, Date>> data = env.fromCollection(genMsgList());
        data.keyBy(t -> t._2())
            .flatMap(new RichFlatMapFunction<Tuple6<Long, String, Integer, String, String, Date>, Object>() {

                // 未调用清除，keyHasBeenSeen.clear();
                private ValueState<Boolean> keyHasBeenSeen;

                @Override
                public void open(Configuration conf) {
                    ValueStateDescriptor<Boolean> desc = new ValueStateDescriptor<>("platform", Types.BOOLEAN);
                    keyHasBeenSeen = getRuntimeContext().getState(desc);
                }

                @Override
                public void flatMap(Tuple6<Long, String, Integer, String, String, Date> tuple, Collector<Object> collector) throws Exception {
                    if (keyHasBeenSeen.value() == null) {
                        collector.collect(tuple);
                        keyHasBeenSeen.update(true);
                    } else {
                        log.info("keyHasBeenSeen value exist! ");
                    }
                }
            }).print();

        env.execute();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testStreamConnect() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 这里注意两个流只有键一致的时候才能连接。 keyBy 的作用是将流数据分区，当 keyed stream 被连接时，他们必须按相同的方式分区。
        DataStream<String> control = env.fromElements("DROP", "IGNORE").keyBy(x -> x);
        DataStream<String> streamOfWords = env.fromElements("Apache", "DROP", "Flink", "IGNORE").keyBy(x -> x);

        control.connect(streamOfWords)
                .flatMap(new RichCoFlatMapFunction<String, String, String>() {

                    private ValueState<Boolean> blocked;

                    @Override
                    public void open(Configuration config) {
                        blocked = getRuntimeContext().getState(new ValueStateDescriptor<>("blocked", Boolean.class));
                    }

                    @Override
                    public void flatMap1(String control_value, Collector<String> out) throws Exception {
                        blocked.update(Boolean.TRUE);
                    }

                    @Override
                    public void flatMap2(String data_value, Collector<String> out) throws Exception {
                        if (blocked.value() == null) {
                            out.collect(data_value);
                        } else {
                            log.info("blocked value exist! ");
                        }
                    }
                })
                .print();

        env.execute();
    }

    /**
     * 产生测试数据
     * @return
     */
    public static List<Tuple6<Long, String, Integer, String, String, Date>> genMsgList() {

        List<Tuple6<Long, String, Integer, String, String, Date>> list = new ArrayList<>();
        for (int i = 0, j = 10; i < j; i++) {
            Tuple6 msg = Tuple6.apply((long) i, i % 2 == 0 ? "android" : "ios", 1, i % 2 == 0 ? "01" : "02", "msg" + i, new Date());
            list.add(msg);
        }
        return list;
    }

}

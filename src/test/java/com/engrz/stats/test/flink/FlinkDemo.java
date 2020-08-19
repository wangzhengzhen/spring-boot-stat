package com.engrz.stats.test.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
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
public class FlinkDemo {

    /**
     * 分组去重
     */
    @Test
    public void testRichFlatMapFunction() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple6<Long, String, Integer, String, String, Date>> data = env.fromCollection(genMsgList());
        data.keyBy(t -> t._2())
            .flatMap(new RichFlatMapFunction<Tuple6<Long, String, Integer, String, String, Date>, Object>() {

                ValueState<Boolean> keyHasBeenSeen;

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
                        log.info("keyHasBeenSeen is null");
                    }
                }
            }).print();

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

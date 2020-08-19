package com.engrz.test.flink.job;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import scala.Tuple6;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobMain {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple6<Long, String, Integer, String, String, Date>> flintstones = env.fromCollection(genMsgList());
        DataStream<Tuple6<Long, String, Integer, String, String, Date>> adults = flintstones.filter(tuple ->  tuple._1() > 2);
        adults.print();
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

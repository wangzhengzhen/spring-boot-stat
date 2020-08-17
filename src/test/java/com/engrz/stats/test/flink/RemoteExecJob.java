package com.engrz.stats.test.flink;

import com.engrz.stats.model.dto.StatsMessageDto;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class RemoteExecJob {

    /**
     * 远程运行任务，执行前先编译 flink-job 模块
     * @throws Exception
     */
    @Test
    public void remoteJob() throws Exception {

        String projectPath = System.getProperty("user.dir");
        String jarFile = projectPath + "/flink-job/target/flink-job-1.0-SNAPSHOT.jar";
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("127.0.0.1", 8081, jarFile);
        DataStream<StatsMessageDto> flintstones = env.fromCollection(genMsgList());
        flintstones.print();
        env.execute("remote job");
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

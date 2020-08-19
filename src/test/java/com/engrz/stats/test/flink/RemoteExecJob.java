package com.engrz.stats.test.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        env.execute("remote job");
    }

}

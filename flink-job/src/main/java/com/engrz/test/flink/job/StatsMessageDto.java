package com.engrz.test.flink.job;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计消息dto
 */
public class StatsMessageDto implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 消息时间
     */
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StatsMessageDto{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                '}';
    }
}

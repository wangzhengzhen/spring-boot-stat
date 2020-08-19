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
     * 平台，android/ios
     */
    private String platform;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 类型
     */
    private String type;

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StatsMessageDto{" +
                "id=" + id +
                ", platform='" + platform + '\'' +
                ", version=" + version +
                ", type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                '}';
    }
}

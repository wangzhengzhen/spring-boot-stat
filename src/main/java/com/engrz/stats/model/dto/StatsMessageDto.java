package com.engrz.stats.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计消息dto
 */
@Data
@EqualsAndHashCode
@ToString
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
     * 时间
     */
    private Date date;

}

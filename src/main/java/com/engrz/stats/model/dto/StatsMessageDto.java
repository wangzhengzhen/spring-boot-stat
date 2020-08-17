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
     * 消息内容
     */
    private String msg;

    /**
     * 消息时间
     */
    private Date date;

}

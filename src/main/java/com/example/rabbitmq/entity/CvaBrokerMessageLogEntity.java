package com.example.rabbitmq.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息记录
 *
 * @author chenjunhua
 * @since 1.0.0 2019-06-04
 */
@TableName("cva_broker_message_log")
@Data
public class CvaBrokerMessageLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.UUID)
	private String messageId;
    /**
     * 消息详情
     */
	private String message;
    /**
     * 重试次数
     */
	private Integer tryCount;
    /**
     * 状态
     */
	private String status;
    /**
     * 
     */
	private Date nextRetry;
    /**
     * 更新时间
     */
	private Date updateDate;

	/**
	 * 创建时间
	 */
	private Date createDate;

}
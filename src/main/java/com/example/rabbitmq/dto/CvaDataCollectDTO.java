package com.example.rabbitmq.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 信息模型
 *
 * @author
 * @since 1.0.0 2019-05-23
 */
@Data
public class CvaDataCollectDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private String cvaId;

	private String accessApp;

	private String accountNo;

	private String appName;

	private String appType;

	private String appVersion;

	private String messageId;

}
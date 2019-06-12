package com.example.rabbitmq.service;

import com.example.rabbitmq.entity.CvaBrokerMessageLogEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息记录
 *
 * @author chenjunhua
 * @since 1.0.0 2019-06-04
 */
public interface CvaBrokerMessageLogService{
    List<CvaBrokerMessageLogEntity> queryStatusAndTimeoutMessage();

    void updateReSend(String messageId, Date updateTime);

    void changeBrokerMessageLogStatus(String messageId, String status, Date updateTime);

    int insertSelective(CvaBrokerMessageLogEntity record);

    int insert(CvaBrokerMessageLogEntity record);

}
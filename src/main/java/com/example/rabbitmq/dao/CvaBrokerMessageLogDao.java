package com.example.rabbitmq.dao;

import com.example.rabbitmq.entity.CvaBrokerMessageLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 消息记录
 *
 * @author chenjunhua
 * @since 1.0.0 2019-06-04
 */
@Mapper
public interface CvaBrokerMessageLogDao{
    /**
     * 查询消息状态为0(发送中) 且已经超时的消息集合
     * @return
     */
    List<CvaBrokerMessageLogEntity> queryStatusAndTimeoutMessage();

    /**
     * 重新发送统计count发送次数 +1
     * @param messageId
     * @param updateTime
     */
    void updateReSend(@Param("messageId") String messageId, @Param("updateDate") Date updateTime);
    /**
     * 更新最终消息发送结果 成功 or 失败
     * @param messageId
     * @param status
     * @param updateTime
     */
    void changeBrokerMessageLogStatus(@Param("messageId") String messageId, @Param("status") String status, @Param("updateDate") Date updateTime);

    int insertSelective(CvaBrokerMessageLogEntity record);

    int insert(CvaBrokerMessageLogEntity record);
}
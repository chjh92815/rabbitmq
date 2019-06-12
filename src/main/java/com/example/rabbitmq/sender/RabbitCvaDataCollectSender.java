package com.example.rabbitmq.sender;

import com.example.rabbitmq.constant.Constant;
import com.example.rabbitmq.dao.CvaBrokerMessageLogDao;
import com.example.rabbitmq.dto.CvaDataCollectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author chenjunhua
 * @title: RabbitCvaDataCollectSender
 * @projectName cva-renren
 * @description: 消息发送
 * @date 2019/6/417:23
 */
@Component
public class RabbitCvaDataCollectSender {
    private static Logger logger = LoggerFactory.getLogger(RabbitCvaDataCollectSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CvaBrokerMessageLogDao cvaBrokerMessageLogDao;
    /**
     * Broker应答后，会调用该方法区获取应答结果(消息是否到交换机中都有callback)
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            logger.info("correlationData："+correlationData);
            String messageId = correlationData.getId();
            if (ack){
                //如果返回成功，则进行更新
                cvaBrokerMessageLogDao.changeBrokerMessageLogStatus(messageId, Constant.MQScheduleStatus.ORDER_SEND_SUCCESS.getValue(), new Date());
            }else {
                //失败进行操作：根据具体失败原因选择重试或补偿等手段
                logger.error("异常处理"+cause);
            }
        }
    };

    /**
     * Broker应答后，会调用该方法区获取应答结果(消息是否到交换机中都有callback)
     */
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            logger.warn("[主体]  " + message);
            logger.warn("[replyCode]  " + replyCode);
            logger.warn("[描述]  " + replyText);
            logger.warn("[exchange]  " + exchange);
            logger.warn("[routingKey]  " + routingKey);
        }
    };

    /**
     * 发送消息方法调用: 构建自定义对象消息
     * @param cvaDataCollectDTO
     * @throws Exception
     */
    public void sendCvaDataCollectDTO(CvaDataCollectDTO cvaDataCollectDTO) {
        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(cvaDataCollectDTO.getMessageId());
        rabbitTemplate.convertAndSend("cvaDataCollectDTO-exchange1", "cvaDataCollectDTO.ABC", cvaDataCollectDTO, correlationData);
    }
}

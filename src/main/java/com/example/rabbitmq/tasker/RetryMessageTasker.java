package com.example.rabbitmq.tasker;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitmq.constant.Constant;
import com.example.rabbitmq.dao.CvaBrokerMessageLogDao;
import com.example.rabbitmq.dto.CvaDataCollectDTO;
import com.example.rabbitmq.entity.CvaBrokerMessageLogEntity;
import com.example.rabbitmq.sender.RabbitCvaDataCollectSender;
import com.example.rabbitmq.service.CvaBrokerMessageLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author chenjunhua
 * @title: RetryMessageTasker
 * @projectName cva-renren
 * @description: 定时任务
 * @date 2019/6/4 17:40
 */
@Component
public class RetryMessageTasker {
    private static Logger logger = LoggerFactory.getLogger(RetryMessageTasker.class);
    @Autowired
    private RabbitCvaDataCollectSender rabbitCvaDataCollectSender;

    @Autowired
    private CvaBrokerMessageLogService cvaBrokerMessageLogService;

    /**
     * 定时任务
     */
    @Scheduled(initialDelay = 5000, fixedDelay = 120000)
    public void reSend(){
        logger.info("-----------RetryMessageTasker定时任务开始-----------");
        //抽取消息状态为0且已经超时的消息集合
        List<CvaBrokerMessageLogEntity> list = cvaBrokerMessageLogService.queryStatusAndTimeoutMessage();
        list.forEach(messageLog -> {
            //投递三次以上的消息
            if(messageLog.getTryCount() >= 3){
                //更新失败的消息
                cvaBrokerMessageLogService.changeBrokerMessageLogStatus(messageLog.getMessageId(), Constant.MQScheduleStatus.ORDER_SEND_FAILURE.getValue(), new Date());
            } else {
                // 重试投递消息，将重试次数递增
                cvaBrokerMessageLogService.updateReSend(messageLog.getMessageId(),  new Date());
                CvaDataCollectDTO cvaDataCollectDTO = JSONObject.parseObject(messageLog.getMessage(), CvaDataCollectDTO.class);
                try {
                    rabbitCvaDataCollectSender.sendCvaDataCollectDTO(cvaDataCollectDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("-----------CvaDataCollectDTO重新发送异常处理-----------");
                }
            }
        });
    }
}

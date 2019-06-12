package com.example.rabbitmq.controller;

import com.alibaba.fastjson.JSON;
import com.example.rabbitmq.constant.Constant;
import com.example.rabbitmq.dto.CvaDataCollectDTO;
import com.example.rabbitmq.entity.CvaBrokerMessageLogEntity;
import com.example.rabbitmq.sender.RabbitCvaDataCollectSender;
import com.example.rabbitmq.service.CvaBrokerMessageLogService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;


/**
 * 信息模型
 *
 * @author chenjunhua
 * @since 1.0.0 2019-05-23
 */
@RestController
@RequestMapping("api/cvadatacollect")
public class CvaDataCollectController {

    @Autowired
    private RabbitCvaDataCollectSender rabbitCvaDataCollectSender;

    @Autowired
    private CvaBrokerMessageLogService cvaBrokerMessageLogService;

    @PostMapping(value="/saveOrUpdte")
    public String saveOrUpdte(HttpServletRequest request, @RequestBody CvaDataCollectDTO dto) {
        dto.setMessageId("Mid_"+ System.currentTimeMillis());
        rabbitCvaDataCollectSender.sendCvaDataCollectDTO(dto);
        // 插入消息记录表数据
        CvaBrokerMessageLogEntity cvaBrokerMessageLogEntity = new CvaBrokerMessageLogEntity();
        // 消息唯一ID
        cvaBrokerMessageLogEntity.setMessageId(dto.getMessageId());
        // 保存消息整体 转为JSON 格式存储入库
        cvaBrokerMessageLogEntity.setMessage(JSON.toJSONString(dto));
        cvaBrokerMessageLogEntity.setTryCount(0);
        // 设置消息状态为0 表示发送中
        cvaBrokerMessageLogEntity.setStatus("0");
        // 设置消息未确认超时时间窗口为 一分钟
        cvaBrokerMessageLogEntity.setNextRetry(DateUtils.addMinutes(new Date(), Integer.parseInt(Constant.MQScheduleStatus.ORDER_TIMEOUT.getValue())));
        cvaBrokerMessageLogEntity.setCreateDate(new Date());
        cvaBrokerMessageLogEntity.setUpdateDate(new Date());
        cvaBrokerMessageLogEntity.setMessageId(dto.getMessageId());
        cvaBrokerMessageLogEntity.setMessage(JSON.toJSONString(dto));
        cvaBrokerMessageLogEntity.setStatus(Constant.MQScheduleStatus.ORDER_SENDING.getValue());
        cvaBrokerMessageLogService.insertSelective(cvaBrokerMessageLogEntity);
        return "成功";
    }
    

}
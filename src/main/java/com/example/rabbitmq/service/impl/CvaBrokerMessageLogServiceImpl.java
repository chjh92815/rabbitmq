package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.dao.CvaBrokerMessageLogDao;
import com.example.rabbitmq.entity.CvaBrokerMessageLogEntity;
import com.example.rabbitmq.service.CvaBrokerMessageLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 消息记录
 *
 * @author chenjunhua
 * @since 1.0.0 2019-06-04
 */
@Service
public class CvaBrokerMessageLogServiceImpl implements CvaBrokerMessageLogService {

    @Autowired
    private CvaBrokerMessageLogDao cvaBrokerMessageLogDao;


    @Override
    public List<CvaBrokerMessageLogEntity> queryStatusAndTimeoutMessage() {
        List<CvaBrokerMessageLogEntity> cvaBrokerMessageLogEntityList = cvaBrokerMessageLogDao.queryStatusAndTimeoutMessage();
        return cvaBrokerMessageLogEntityList;
    }

    @Override
    public void updateReSend(String messageId, Date updateTime) {
        cvaBrokerMessageLogDao.updateReSend(messageId, updateTime);
    }

    @Override
    public void changeBrokerMessageLogStatus(String messageId, String status, Date updateTime) {
        cvaBrokerMessageLogDao.changeBrokerMessageLogStatus(messageId, status, updateTime);
    }

    @Override
    public int insertSelective(CvaBrokerMessageLogEntity record) {
        return cvaBrokerMessageLogDao.insertSelective(record);
    }

    @Override
    public int insert(CvaBrokerMessageLogEntity record) {
        return cvaBrokerMessageLogDao.insert(record);
    }
}
package com.example.rabbitmq.receiver;

import com.example.rabbitmq.dto.CvaDataCollectDTO;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chenjunhua
 * @title: RabbitCvaDataCollectReceiver
 * @projectName cva-renren
 * @description: 消费
 * @date 2019/6/417:52
 */
@Component
public class RabbitCvaDataCollectReceiver {

    /**
     * @description: TODO
     * @RabbitListener 消息监听，可配置交换机、队列、路由key该注解会创建队列和交互机 并建立绑定关系
     * @RabbitHandler 标识此方法如果有消息过来，消费者要调用这个方法
     * @Payload 消息体
     * @Headers 消息头
     * @param
     * @return void
     * @throws
     * @author chenjunhua
     * @date 2019/6/4 17:55
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "cvaDataCollectDTO-queue1"),
            exchange = @Exchange(name = "cvaDataCollectDTO-exchange1",type = "topic"),
            key = "cvaDataCollectDTO.ABC"
    ))
    @RabbitHandler
    public void onCvaDataCollectMessage(@Payload CvaDataCollectDTO cvaDataCollectDTO, @Headers Map<String,Object> headers,
                                        Channel channel) throws Exception{
        //消费者操作
        System.out.println("------收到消息，开始消费------");
        System.out.println("cvaID："+cvaDataCollectDTO.getCvaId());

        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("deliveryTag:" + deliveryTag);
        //现在是手动确认消息 ACK
        channel.basicAck(deliveryTag,false);
    }
}

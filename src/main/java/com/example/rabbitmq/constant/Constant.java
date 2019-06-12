package com.example.rabbitmq.constant;

/**
 * @author chenjunhua
 * @title: Constant
 * @projectName rabbitmq
 * @description: TODO
 * @date 2019/6/1114:08
 */
public interface Constant {
    enum MQScheduleStatus {
        /**
         * 发送中
         */
        ORDER_SENDING ("0"),
        /**
         * 发送成功
         */
        ORDER_SEND_SUCCESS ("1"),

        /**
         * 发送失败
         */
        ORDER_SEND_FAILURE("2"),

        /**
         * 分钟超时单位:min
         */
        ORDER_TIMEOUT("1");

        private String value;

        MQScheduleStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

package com.example.rabbitmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author chenjunhua
 * @title: TaskSchedulerConfig
 * @projectName cva-renren
 * @description: 定时任务配置
 * @date 2019/6/4 14:44
 */
@Configuration
@EnableScheduling
public class TaskSchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());
    }

    /**
     * @description: 定时任务线程池
     * @param
     * @return ${return_type}
     * @throws
     * @author chenjunhua
     * @date 2019/6/4 14:47
     */
    @Bean
    public Executor taskScheduler(){
        return Executors.newScheduledThreadPool(3);
    }
}

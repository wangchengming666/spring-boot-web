package com.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Direct模式
 * 
 * @author cm_wang
 *
 */
@Configuration
public class RabbitMQDirectConfig {

	public static final String DIRECT_QUEUE = "direct.queue";
    /**
     * Direct模式
     * @return
     */
    @Bean
    public Queue directQueue() {
        // 第一个参数是队列名字， 第二个参数是指是否持久化
        return new Queue(DIRECT_QUEUE, true);
    }
}

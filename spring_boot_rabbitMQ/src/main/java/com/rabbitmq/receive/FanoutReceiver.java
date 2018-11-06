package com.rabbitmq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.config.RabbitMQFanoutConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * fanout模式消费者
 * 
 * @author cm_wang
 *
 */
@Component
@Slf4j
public class FanoutReceiver {

	// queues是指要监听的队列的名字
	@RabbitListener(queues = RabbitMQFanoutConfig.FANOUT_QUEUE)
	public void receiveFanout(Object obj) {
		log.info("receiveFanout收到消息" + obj.toString());
	}
}

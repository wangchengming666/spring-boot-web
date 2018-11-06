package com.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fanout模式
 * 
 * @author cm_wang
 *
 */
@Configuration
public class RabbitMQFanoutConfig {

	public static final String FANOUT_QUEUE = "fanout.queue";

	public static final String FANOUT_EXCHANGE = "fanout.exchange";
	
	@Bean
	public Queue fanoutQueue() {
		return new Queue(FANOUT_QUEUE);
	}

	/**
	 * Fanout模式 Fanout 就是我们熟悉的广播模式或者订阅模式，
	 * 给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
	 * 
	 * @return
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE);
	}

	@Bean
	public Binding fanoutBinding() {
		return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
	}
}

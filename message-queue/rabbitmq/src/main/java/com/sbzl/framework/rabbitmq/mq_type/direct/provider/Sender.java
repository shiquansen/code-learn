package com.sbzl.framework.rabbitmq.mq_type.direct.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Administrator
 *
 */
@Component("directSender")
public class Sender {

	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;

	/**
	 * 	exchange 交换器名称
	 */
	@Value("${direct.provider.mq.config.exchange}")
	private String exchange;

	/**
	 * //routingkey 路由键
	 */
	@Value("${direct.provider.mq.config.queue.info.routing.key}")
	private String routingkey;

	/**
	 * 发送消息的方法
	 */
	public void send(String msg){
		//向消息队列发送消息
		//参数一：交换器名称。
		//参数二：路由键
		//参数三：消息
		this.rabbitAmqpTemplate.convertAndSend(this.exchange, this.routingkey, msg);
	}
}

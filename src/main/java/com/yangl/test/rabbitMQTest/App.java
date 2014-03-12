package com.yangl.test.rabbitMQTest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws InterruptedException {

		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
				"app.xml");
		RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
		template.convertAndSend("Hello, world -- ");
		long start = System.currentTimeMillis();
		for(int i = 0; i< 2500; i++){
			template.convertAndSend("INFO: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@259881f0: defining beans [connectionFactory,amqpTemplate,org.springframework.amqp.rabbit.core.RabbitAdmin#0,org.springframework.amqp.core.Queue#0,org.springframework.amqp.rabbit.config.BindingFactoryBean#0,org.springframework.amqp.core.TopicExchange#0]; root of factory hierarchyHello, world -- " + i);
		}
		System.out.println(System.currentTimeMillis() - start);
		ctx.destroy();
		System.out.println("colse");
	}
}

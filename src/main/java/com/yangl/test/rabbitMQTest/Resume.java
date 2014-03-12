package com.yangl.test.rabbitMQTest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 * 
 */
public class Resume {
	public static void main(String[] args) throws InterruptedException {

		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
				"app.xml");
		RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
		long start = System.currentTimeMillis();
		for(int i = 0; i< 100; i++){
			template.convertAndSend("Hello, world -- " + i);
		}
		System.out.println(System.currentTimeMillis() - start);
		ctx.destroy();
		System.out.println("colse");
	}
}

package com.yangl.test.rabbitmq.topic;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sent {
	private static final String EXCHANGE_NAME = "ex_topic";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.TOPIC);
		String routing = "topic.error.one";
		for(int i = 0; i < 10; i++){
			String message = "topic error one : " + i;
			channel.basicPublish(EXCHANGE_NAME, routing, null, message.getBytes());
		}
		System.out.println("########################");
		String routingTwo = "topic.error.two";
		for(int i = 0; i < 10; i++){
			String message = "topic error two : " + i;
			channel.basicPublish(EXCHANGE_NAME, routingTwo, null, message.getBytes());
		}
		System.out.println("########################");
		String routingThree = "topic.info.one";
		long start = System.currentTimeMillis();
		for(int i = 0; i < 100000; i++){
			channel.basicPublish(EXCHANGE_NAME, routingThree, null, ("topic info one : " + i).getBytes());
		}
		System.out.println(System.currentTimeMillis() - start);
		channel.close();
		connection.close();
	}
}

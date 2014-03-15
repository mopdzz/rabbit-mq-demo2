package com.yangl.test.rabbitmq.routing;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sent {
	private static final String EXCHANGE_NAME = "exchange";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT); // rounting
																		// 模式
		String rountingKeyOne = "error";
		for (int i = 0; i < 10; i++) {
			String messageOne = "this is a error logs : " + i;
			channel.basicPublish(EXCHANGE_NAME, rountingKeyOne, null, messageOne.getBytes());
		}
		System.out.println("#############################");
		
		String rountingKeyTwo = "info";
		for (int i = 0; i < 10; i++) {
			String messageTwo = "this is a info logs : " + i;
			channel.basicPublish(EXCHANGE_NAME, rountingKeyTwo, null, messageTwo.getBytes());
		}
		System.out.println("#############################");
		
		String rountingKeyThree = "all";
		for (int i = 0; i < 10; i++) {
			String messageThree = "this is a all logs : " + i;
			channel.basicPublish(EXCHANGE_NAME, rountingKeyThree, null, messageThree.getBytes());
		}
		
		channel.close();
		connection.close();
	}
}

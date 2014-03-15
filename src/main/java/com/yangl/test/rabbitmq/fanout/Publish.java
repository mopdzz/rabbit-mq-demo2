package com.yangl.test.rabbitmq.fanout;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publish {
	private static final String EXCHANGE_NAME = "fanout";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);
		long start = System.currentTimeMillis();
		for(int i = 0; i< 20000; i++){
			String message = "message " + i;
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
//			System.out.println("Sent " + message);
		}
		System.out.println(System.currentTimeMillis() - start);
		channel.close();
		connection.close();
	}
}

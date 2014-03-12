package com.yangl.test.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	private static final String Queue_Name = "hello";
	
	public static void main(String[] args) throws IOException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(Queue_Name, false, false, false, null);
		String message = "Hello world!";
		channel.basicPublish("", Queue_Name, null, message.getBytes());
		System.out.println("Sent : " + message);
		channel.close();
		connection.close();
	}
}

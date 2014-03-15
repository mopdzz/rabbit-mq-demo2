package com.yangl.test.rabbitmq.fanout;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Subscribe {
	private static final String EXCHANGE_NAME = "fanout";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);
		
		String queueName = "queue2";
		channel.queueDeclare(queueName, false, false, false, null);
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			System.out.println(queueName + " " + new String(delivery.getBody()));
		}
	}
}

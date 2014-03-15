package com.yangl.test.rabbitmq.topic;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Receive {
	private static final String EXCHANGE_NAME = "ex_topic";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.TOPIC);
		
		String queueName = "queue_topic_1";
		channel.queueDeclare(queueName, false, false, false, null);
		String routing = "topic.*.one";
		channel.queueBind(queueName, EXCHANGE_NAME, routing);

		System.out.println("Waiting for message.");
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		long start = System.currentTimeMillis();
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			String routingKey = delivery.getEnvelope().getRoutingKey();
			System.out.println((System.currentTimeMillis() - start) + "   Received " + routingKey + " : " + message);
		}
	}
}

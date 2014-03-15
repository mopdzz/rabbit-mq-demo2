package com.yangl.test.rabbitmq.rpc;

import java.util.UUID;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Client {
	private Connection connection;
	private Channel channel;
	private String requestQueueName = "rpc_queue";
	private String replayQueueName;
	private QueueingConsumer consumer;

	public Client() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();

		replayQueueName = channel.queueDeclare().getQueue();
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(replayQueueName, consumer);
	}

	public String call(String message) throws Exception {
		String response = null;
		String corrId = UUID.randomUUID().toString();

		BasicProperties props = new BasicProperties();
		props.setReplyTo(replayQueueName);
		props.setCorrelationId(corrId);

		channel.basicPublish("", requestQueueName, props, message.getBytes());

		System.out.println("--- " + message);
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				response = new String(delivery.getBody(), "UTF-8");
				break;
			}
		}

		return response;
	}

	public void close() throws Exception {
		connection.close();
	}

	public static void main(String[] args) {
		Client client = null;
		String response = null;
		try {
			client = new Client();
			System.out.println("Requesting fib(30)");
			response = client.call("30");
			System.out.println("got : " + response);

			System.out.println("Requesting fib(-1)");
			response = client.call("-1");
			System.out.println("got : " + response);

			System.out.println("Requesting fib(a)");
			response = client.call("a");
			System.out.println("got : " + response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

package datadump.consumer;

import com.rabbitmq.client.*;
import java.io.IOException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;

public class Consumer2 {
    private static final Logger logger = Logger.getLogger(Consumer2.class);
    private final static String QUEUE_NAME = "queue2";

    public void receiveMsg() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("172.19.4.100");
            factory.setUsername("admin");
            factory.setPassword("123456");
            factory.setPort(5672);

            Connection connection = null;
            connection = factory.newConnection();

            Channel channel = null;
            channel = connection.createChannel();

            com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //body就是从队列中获取的数据
                    String msg = new String(body);
                    logger.info("接收2：" + msg);
                }
            };

            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


package com.javazh;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Objects;

/**
 * @Author zhangh
 * @Date 2020/10/16 16:31
 **/
public class JmsConsumer {
    private final static String ACTIVEMQ_URL = "tcp://192.168.0.220:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        //生产消息
        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建会话
        //两个参数，第一个事务，第二个签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地 具体是队列还是topic
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);
        while (true) {
            TextMessage receive = (TextMessage) consumer.receive();
            if (Objects.nonNull(receive)) {
                System.out.println("*****消费者接受消息:" + receive.getText());
            } else {
                break;
            }
        }
        consumer.close();
        session.close();
        connection.close();
    }
}

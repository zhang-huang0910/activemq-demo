package com.javazh;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author zhangh
 * @Date 2020/10/16 13:12
 **/
public class ActivemqDemo {
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
        //5.创建消息的生产各者
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 3; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("msg: " + i);
            producer.send(textMessage);
        }
        //关闭资源
        producer.close();
        session.close();
        connection.close();
        System.out.println("消息生产成功！");

    }
}

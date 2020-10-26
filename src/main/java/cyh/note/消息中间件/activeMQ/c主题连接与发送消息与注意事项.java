package cyh.note.消息中间件.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class c主题连接与发送消息与注意事项 {

    public static void main(String[] args) throws Exception {
        主题简介();
        主题生产者();
//        主题消费者();
    }

    private static void 主题消费者() throws Exception {
        // 创建连接工厂，若mq的默认用户名密码改了，那么直接在构造方法中传递用户名密码即可
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 获取连接,并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 创建会话
        // 两个参数 事务、签收 ，后续会讲到这两个参数的含义
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是主题还是主题？）
        // 主题
        Topic topic = session.createTopic("topic-cyh");
        // 创建消费者
        MessageConsumer consumer = session.createConsumer(topic);

        consumer.setMessageListener((message -> {
            if (message != null && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收到的消息为：" + textMessage.getText());
                } catch (JMSException e) {

                }
            }
        }));
        System.in.read();
        // 关闭连接
        consumer.close();
        session.close();
        connection.close();
    }

    private static void 主题生产者() throws Exception {
        // 创建连接工厂，若mq的默认用户名密码改了，那么直接在构造方法中传递用户名密码即可
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 获取连接,并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 创建会话
        // 两个参数 事务、签收 ，后续会讲到这两个参数的含义
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建主题（具体是主题还是主题？）
        Topic topic = session.createTopic("topic-cyh");

        // 给该主题创建消息生产者
        MessageProducer messageProducer = session.createProducer(topic);
        // 创建消息
        TextMessage msg = session.createTextMessage("这是我要发送的消息");
        // 发送消息
        messageProducer.send(msg);
        // 断开连接
        messageProducer.close();
        session.close();
        connection.close();
    }

    private static void 主题简介() {
        /**
         1、生产者将消息发送到topic中，每个消息可以有多个消费者，属于1：N关系
         2、生产者和消费者有时间上的相关性，订阅某一个主题的消费者只能消费自它订阅之后发布的消息
         3、生产者生产时，topic不保存消息它是无状态的不落地，假如无人订阅就去生产，那么这条消息其实是废消息，所以一般都是消费者先启动，再启动生产者
         */
    }

}

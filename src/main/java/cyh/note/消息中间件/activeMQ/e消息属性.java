package cyh.note.消息中间件.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class e消息属性 {
    public static void main(String[] args) throws Exception {
        发送带消息属性的数据();
        接收带消息属性的数据();
    }

    private static void 接收带消息属性的数据() throws Exception {
        // 创建连接工厂，若mq的默认用户名密码改了，那么直接在构造方法中传递用户名密码即可
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 获取连接,并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 创建会话
        // 两个参数 事务、签收 ，后续会讲到这两个参数的含义
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主题？）
        // 队列
        Queue queue = session.createQueue("queue01");
        // 创建消费者
        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        /**
                         * 获取消息属性
                         */
                        String 是否是vip = textMessage.getStringProperty("是否是vip");
                        System.out.println("接收到的消息是否是vip：" + 是否是vip);

                        System.out.println("接收到的消息为：" + textMessage.getText());
                    } catch (JMSException e) {

                    }
                }
            }
        });
        System.in.read();
        // 关闭连接
        consumer.close();
        session.close();
        connection.close();
    }

    private static void 发送带消息属性的数据() throws Exception {
        // 创建连接工厂，若mq的默认用户名密码改了，那么直接在构造方法中传递用户名密码即可
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 获取连接,并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 创建会话
        // 两个参数 事务、签收 ，后续会讲到这两个参数的含义
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建队列（具体是队列还是主题？）
        Queue queue = session.createQueue("queue01");
        // 给该队列创建消息生产者
        MessageProducer messageProducer = session.createProducer(queue);
        // 创建消息
        TextMessage msg = session.createTextMessage("这是我要发送的文本消息");

        /**
         * 设置消息属性
         */
        msg.setStringProperty("是否是vip","1");

        // 发送消息
        messageProducer.send(msg);

        // 断开连接
        messageProducer.close();
        session.close();
        connection.close();
    }
}

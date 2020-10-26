package cyh.note.消息中间件.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class d接收发送其他类型消息体数据 {

    public static void main(String[] args) throws Exception {
        发送消息();
        接收消息();
    }

    private static void 接收消息() throws Exception {
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
                /**
                 * 接收文本消息
                 */
                if (message != null && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("接收到的消息为：" + textMessage.getText());
                    } catch (JMSException e) {

                    }
                }
                /**
                 * 接收map消息
                 */
                if (message != null && message instanceof MapMessage) {
                    MapMessage mapMessage = (MapMessage) message;
                    try {
                        System.out.println("接收到的消息为：" + mapMessage.getString("k1"));
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

    private static void 发送消息() throws JMSException {
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

        /**
         * 发送文本消息
         */
        // 创建消息
        TextMessage msg = session.createTextMessage("这是我要发送的文本消息");
        // 发送消息
        messageProducer.send(msg);

        /**
         * 发送map消息
         */
        MapMessage mapMsg = session.createMapMessage();
        mapMsg.setString("k1","map类型消息---v1");
        messageProducer.send(mapMsg);

        // 断开连接
        messageProducer.close();
        session.close();
        connection.close();
    }
}

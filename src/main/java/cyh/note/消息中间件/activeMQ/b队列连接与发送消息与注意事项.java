package cyh.note.消息中间件.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class b队列连接与发送消息与注意事项 {

    public static void main(String[] args) throws Exception {
        队列消息的基本连接与基本发送();
        队列阻塞的消费者();
        队列监听的消费者();

        多消费者接收消息规则注意事项();

    }

    private static void 多消费者接收消息规则注意事项() {
        /**
         1：队列中塞入6条消息，A接收者先启动，B接收者再启动
            A接收到6条，B接收不到

         2：先启动A和B接收者，再塞入6条消息
            A和B一人接收一半，类似于负载均衡
         */

    }



    private static void 队列监听的消费者() throws Exception {
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


    private static void 队列阻塞的消费者() throws JMSException {
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

        while (true) {
            // 从队列中获取消息，因为发送的是文本消息，所以这个强制转换为文本消息
            // receive：若队列中没有消息，该方法会一直等待，也可以传参设置最长等待时间（毫秒）
            // 当receive方法在等待时，MQ中该队列的消费者数量就会+1，若停止项目或者设置的等待时间超时了，那么消费者数量就会复原
            TextMessage message = (TextMessage) consumer.receive();
            if (null != message) {
                System.out.println("接收到的消息为：" + message.getText());
            } else {
                break;
            }
        }
        // 关闭连接
        consumer.close();
        session.close();
        connection.close();
    }


    private static void 队列消息的基本连接与基本发送() throws JMSException {
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
        TextMessage msg = session.createTextMessage("这是我要发送的消息");
        // 发送消息
        messageProducer.send(msg);
        // 断开连接
        messageProducer.close();
        session.close();
        connection.close();
    }


}

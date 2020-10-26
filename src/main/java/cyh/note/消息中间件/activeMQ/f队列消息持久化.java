package cyh.note.消息中间件.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class f队列消息持久化 {

    public static void main(String[] args) throws Exception {
        消息持久化与非持久化设置();
    }

    private static void 消息持久化与非持久化设置() throws Exception {
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
         * 消息的非持久化
         *
         * MQ服务器宕机重启后，原先保存的消息将会消失
         */
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        /**
         * 消息的持久化（默认）
         *
         * MQ服务器宕机重启后，消费过的记录会被清空，但是还没有被消费的消息还会保存在那
         */
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);




        // 创建消息
        TextMessage msg = session.createTextMessage("这是我要发送的文本消息");

        // 发送消息
        messageProducer.send(msg);

        // 断开连接
        messageProducer.close();
        session.close();
        connection.close();
    }
}

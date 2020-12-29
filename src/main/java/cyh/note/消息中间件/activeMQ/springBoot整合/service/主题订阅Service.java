package cyh.note.消息中间件.activeMQ.springBoot整合.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.util.UUID;

/**
 * 若需要发送主题消息
 * 则 yml 配置文件中的 pub-sub-domain 参数需要改为 true
 */
@Component
public class 主题订阅Service {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic activeMqTopic;

    @Scheduled(fixedDelay = 3000)
    public void 主题生产者(){
        String substring = UUID.randomUUID().toString().substring(0, 6);
        System.out.println("ActiveMQ 主题 定时发送主题消息：" + substring);
        jmsMessagingTemplate.convertAndSend(activeMqTopic,"主题消息：" + substring);
    }

    @JmsListener(destination = "${myActiveTopic}")
    public void 主推消费者(TextMessage textMessage) throws Exception{
        System.out.println("ActiveMQ 主题 消费者收到订阅的主题消息：" + textMessage.getText());
    }

}

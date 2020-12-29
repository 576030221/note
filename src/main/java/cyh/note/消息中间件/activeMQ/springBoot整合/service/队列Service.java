package cyh.note.消息中间件.activeMQ.springBoot整合.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.TextMessage;
import java.util.UUID;

/**
 * 若需要发送队列消息
 * 则 yml 配置文件中的 pub-sub-domain 参数需要改为false
 */
@Component
public class 队列Service {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    // 将ConfigBean中的queue方法得到的队列得到
    @Autowired
    private Queue activeMqQueue;

    public void 生产一次普通队列消息() throws Exception{
        jmsMessagingTemplate.convertAndSend(activeMqQueue,"***** 队列发送一次消息");
    }

    @Scheduled(fixedDelay = 3000)
    public void 间隔3秒钟定时投递() throws Exception{
        String substring = UUID.randomUUID().toString().substring(0, 6);
        System.out.println("ActiveMQ 队列 定时发送消息：" + substring);
        jmsMessagingTemplate.convertAndSend(activeMqQueue,"* 队列消息 +" + substring);
    }

    /**
     * 队列监听
     * 添加该注解即可监听 ${myqueue} 配置文件中该参数 名称对应的队列中的消息
     * 队列收到一条消息则监听获取到一条消息
     * @param textMessage
     * @throws Exception
     */
    @JmsListener(destination = "${myActiveQueue}")
    public void 消费者监听  (TextMessage textMessage) throws Exception{
        System.out.println("ActiveMQ 队列 消费者收到消息：" + textMessage.getText());
    }

}

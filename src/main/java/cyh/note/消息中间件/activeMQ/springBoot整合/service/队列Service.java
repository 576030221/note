package cyh.note.消息中间件.activeMQ.springBoot整合.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class 队列Service {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    // 生产消息
    public void produceMsg() throws Exception{
        System.out.printf(queue.getQueueName());
        jmsMessagingTemplate.convertAndSend(queue,"***** 发送消息");
    }
}

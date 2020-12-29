package cyh.note.消息中间件.activeMQ.springBoot整合.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

@Component
@EnableJms  // 开启JSMS适配注解
public class ConfigBean {
    // 队列名称 从配置文件中获取
    @Value("${myActiveQueue}")
    private String myActiveQueue;
    // 主题名称 从配置文件中获取
    @Value("${myActiveTopic}")
    private String myActiveTopic;

    @Bean
    public Queue activeMqQueue(){
        return new ActiveMQQueue(myActiveQueue);
    }

    @Bean
    private Topic activeMqTopic(){
        return new ActiveMQTopic(myActiveTopic);
    }
}
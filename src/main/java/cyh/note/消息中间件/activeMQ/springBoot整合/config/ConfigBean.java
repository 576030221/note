package cyh.note.消息中间件.activeMQ.springBoot整合.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@EnableJms  // 开启JSMS适配注解
public class ConfigBean {
    // 队列名称
    @Value("${myqueue}")
    private String myQueue;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }
}
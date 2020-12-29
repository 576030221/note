package cyh.note.消息中间件.activeMQ.springBoot整合.controller;

import cyh.note.消息中间件.activeMQ.springBoot整合.service.队列Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("activeMq")
public class ActiveMq {

    @Autowired
    private 队列Service d队列Service;

    @RequestMapping("sendMsg")
    public void sendMsg() throws Exception {
        d队列Service.生产一次普通队列消息();
    }

}

package cyh.note.消息中间件.activeMQ.springBoot整合.controller;


import cyh.note.消息中间件.activeMQ.springBoot整合.service.队列Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Controller {

    @Resource
    private 队列Service d队列Service;

    @RequestMapping("send")
    public void send() throws Exception {
        d队列Service.produceMsg();
    }
}

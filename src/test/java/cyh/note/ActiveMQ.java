package cyh.note;

import cyh.note.消息中间件.activeMQ.springBoot整合.service.队列Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@SpringBootTest(classes = NoteApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ActiveMQ {

    @Resource
    private 队列Service d队列Service;

    @Test
    public void testSend() throws Exception{
        d队列Service.produceMsg();
    }

}

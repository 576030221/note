package cyh.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling   // 开启定时器的功能
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteApplication.class, args);
    }

}

package online.connectjagwar.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NotificationServiceApplication {
    public static void main(String[] args){
        System.out.print("Hey, Jagwar");
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}

package online.connectjagwar.notification_service.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import online.connectjagwar.notification_service.model.NotificationEvent;

@Service
public class NotificationConsumer {
    private final NotificationDispatcher dispatcher;

    public NotificationConsumer(NotificationDispatcher dispatcher){
        this.dispatcher = dispatcher;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void listen(NotificationEvent event){
        System.out.println("Recieved message from rabbitmq for: " + event.getNotificationType());
        dispatcher.dispatch(event);
    }
}

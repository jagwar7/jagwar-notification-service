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


    /**
     * @param event
     * EVENT WILL BE MONITORED IN RABBIT QUEUE AND WILL PASSED TO DISPATCHER
     */

    @RabbitListener(queues = "${rabbitmq.email.queue.name}", containerFactory = "rabbitContainerFactory")
    public void listen(NotificationEvent event){
        System.out.println("Recieved message from rabbitmq for: " + event.getNotificationType());
        dispatcher.dispatch(event);
    }
}


        // System.out.println("Recieved message from rabbitmq for: " + event.getNotificationType());
        // if("Jagwar".equals(event.getRecipient().getName())){
        //     System.out.println("Simulating fail for user Jagwar");
        //     throw new RuntimeException("Failing to send");
        // }else{
        //     dispatcher.dispatch(event);
        // }
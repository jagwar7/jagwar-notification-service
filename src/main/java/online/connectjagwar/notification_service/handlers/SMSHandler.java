package online.connectjagwar.notification_service.handlers;

import org.springframework.stereotype.Component;

import online.connectjagwar.notification_service.enums.NotificationType;
import online.connectjagwar.notification_service.interfaces.INotificationHandler;
import online.connectjagwar.notification_service.model.NotificationEvent;


@Component
public class SMSHandler implements INotificationHandler {
    
    public void send(NotificationEvent event){

    }

    public NotificationType getNotificationType(){
        return NotificationType.SMS;
    }
}

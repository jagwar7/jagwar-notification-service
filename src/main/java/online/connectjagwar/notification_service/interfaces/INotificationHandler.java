package online.connectjagwar.notification_service.interfaces;

import online.connectjagwar.notification_service.enums.NotificationType;
import online.connectjagwar.notification_service.model.NotificationEvent;

public interface INotificationHandler {
    void send(NotificationEvent event);
    NotificationType getNotificationType();  // RETURN : EMAIL, SMS , WHATSAPP ... ENUMS
}

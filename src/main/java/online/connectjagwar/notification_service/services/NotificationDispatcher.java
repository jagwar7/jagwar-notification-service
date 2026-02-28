package online.connectjagwar.notification_service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import online.connectjagwar.notification_service.enums.NotificationType;
import online.connectjagwar.notification_service.interfaces.INotificationHandler;
import online.connectjagwar.notification_service.model.NotificationEvent;

@Service
public class NotificationDispatcher {
/**
 * TODO:
 *      1: NOTIFICATION DISPATCHER WILL CONTAIN A MAP
 *          TYPE: 
 *              NOTIFICATION TYPE AS KEY (EG: EMAIL, SMS, WHATSAPP)
 *              CORRESPONDING NOTIFICATOIN HANDLER FOR KEY TYPE
 *              EXAMPLE: <EMAIL, EMAIL_HANDLER>
 *      
 *      2: CONSTRUCTOR WILL TAKE A LIST AND MAKE HASHMAP BY ITERATING ALL ITEMS
 *      
 *      3: DISPATCH :
 *          GET CORRESPONDING HANDLER BY NOTIFICATION_TYPE
 *          CALL CORRESPONDING SEND METHOD OF HANLDER
 *              HANDLER.SEND()  (EG: emailHandler.send() to send email)   
 */
    private final Map<NotificationType, INotificationHandler> notificationsHandlerMap = new HashMap<>();

    public NotificationDispatcher(List<INotificationHandler> handlers){
        for(INotificationHandler handler : handlers){
            notificationsHandlerMap.put(handler.getNotificationType(), handler);
        }
    }

    public void dispatch(NotificationEvent event){
        // GET CORRESPONDING HANDLER BY NOTIFICAITON TYPE (EG: EMAIL, SMS, WHATSAPP)
        INotificationHandler handler = notificationsHandlerMap.get(event.getNotificationType()); 
        if(handler != null){
            handler.send(event);
        }else{
            System.out.println("No handler found for : " + event.getNotificationType());
        }
    }
}

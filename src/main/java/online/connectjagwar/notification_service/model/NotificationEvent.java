package online.connectjagwar.notification_service.model;

import java.util.Map;

import org.springframework.messaging.handler.annotation.Payload;

import online.connectjagwar.notification_service.enums.NotificationType;
import online.connectjagwar.notification_service.interfaces.INotificationPayload;

/**
 * THIS CLASS CONTAINS: NOTIFICATION TYPE , TEMPLATE NAME, AND THE PAYLOAD
 * WHICH WILL CONTAIN THE ACTUAL CONTENT OF MESSAGE.
 * 
 * TYPE : EMAIL, SMS , WHATSAPP
 * TEMPLATE_NAME : PAYMENT_SUCCESS, WELCOME_ON_SIGN_UP, PAYMENT_FALIURE, ORDER_PLACED, ORDER_DISPATCHED
 * PAYLOAD : {order_id: abcxyz, amount: 500, date: 10/10/2025, payment method: VISA}, {name: jagwar, welcomeTo: amazon.com, otp: 123456} 
 */


public class NotificationEvent {
    private NotificationType notificationType;
    private String templateName;
    private Recipient recipient;
    private INotificationPayload payload;

    public NotificationType getNotificationType(){
        return notificationType;
    }
    public void setNotificationType(NotificationType notificationType){
        this.notificationType = notificationType;
    }


    
    public String getTemplateName(){
        return templateName;
    }
    public void setTemplateName(String templateName){
        this.templateName = templateName;
    }
    // ----------------------------------------------------------



    // GETTER AND SETTER FOR RECIPIENT---------------------------
    public Recipient getRecipient(){
        return recipient;
    }
    public void setRecipient(Recipient recipient){
        this.recipient = recipient;
    }
    // ----------------------------------------------------------



    // GETTER AND SETTER FOR PAYLOADS----------------------------
    public INotificationPayload getPayload(){
        return payload;   
    }
    public void setPayload(INotificationPayload payload){
        this.payload = payload;
    }
    // ----------------------------------------------------------

}

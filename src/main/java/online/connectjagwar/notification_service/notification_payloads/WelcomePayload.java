package online.connectjagwar.notification_service.notification_payloads;

import online.connectjagwar.notification_service.interfaces.INotificationPayload;

public class WelcomePayload implements INotificationPayload{
    private String userName;
    private String welcomeLink;

    public WelcomePayload(){
        //  EMPTY CONSTRUCTOR FOR SERIALIZATION
    }

    public WelcomePayload(String userName, String welcomeLink){
        this.userName = userName;
        this.welcomeLink = welcomeLink;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getWelcomeLink(){
        return welcomeLink;
    }
    public void setWelcomeLink(String welcomeLink){
        this.welcomeLink = welcomeLink;
    }
    
}

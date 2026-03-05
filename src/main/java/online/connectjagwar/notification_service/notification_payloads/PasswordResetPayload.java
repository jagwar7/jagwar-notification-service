package online.connectjagwar.notification_service.notification_payloads;

import online.connectjagwar.notification_service.interfaces.INotificationPayload;

public class PasswordResetPayload implements INotificationPayload{
    private String resetLink;

    public PasswordResetPayload(){
        // EMPTY CONSTRUCTOR FOR JSON SERIALIZATION
    }

    public PasswordResetPayload(String resetLink){
        this.resetLink = resetLink;
    }


    //------------------------------------------------------------
    // GETTER AND SETTERS
    public String getResetLink(){
        return this.resetLink;
    }
    public void setResetLink(String resetLink){
        this.resetLink = resetLink;
    }
    //------------------------------------------------------------

}

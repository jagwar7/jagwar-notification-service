package online.connectjagwar.notification_service.handlers;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import online.connectjagwar.notification_service.enums.NotificationType;
import online.connectjagwar.notification_service.interfaces.INotificationHandler;
import online.connectjagwar.notification_service.model.NotificationEvent;
import online.connectjagwar.notification_service.services.TemplateService;


@Component
public class EmailHandler implements INotificationHandler {
/**
 *  TODO:
 *      1: GET TEMPLATE SERVICE TO RENDER HTML
 *      2: GET JAVA MAIL SENDER
 * 
 *      3: IMPLEMENT SEND METHOD
 *          1: GET HTML CONTENT AS STRING 
 *          2: CREATE MIME MESSAGE
 *          3: CREATE MESSAGE HELPER 
 *  
 */
    private final TemplateService templateService;
    private final JavaMailSender mailSender;

    public EmailHandler(TemplateService templateService, JavaMailSender mailSender){
        this.templateService = templateService;
        this.mailSender = mailSender;
    }

    public void send(NotificationEvent event){
        String htmlContent = templateService.renderHTML(event.getTemplateName(), event.getPayload());
        
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(event.getRecipient().getEmail());
            mimeMessageHelper.setSubject(event.getTemplateName());
            mimeMessageHelper.setText(htmlContent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message : ",e);
        }
    }

    public NotificationType getNotificationType(){
        return NotificationType.EMAIL;
    }
}

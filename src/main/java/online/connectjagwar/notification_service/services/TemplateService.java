package online.connectjagwar.notification_service.services;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import online.connectjagwar.notification_service.interfaces.INotificationPayload;

/**
 * THIS FILE WILL TAKE PAYLOAD AND PROCESS PAYLOAD DATA AND RETURN HTML 
 */


@Service
public class TemplateService {
    private final TemplateEngine thymeleafEngine;

    public TemplateService(TemplateEngine thymeleafEngine){
        this.thymeleafEngine = thymeleafEngine;
    }

    public String renderHTML(String templateString, INotificationPayload payload){
        Context context = new Context();                              // CREATE CONTEXT BUCKET
        
        context.setVariable("data", payload);                    // PASS PAYLOAD TO CONTEXT , IT WILL BE DECODED

        return thymeleafEngine.process("email/", context);   // RETURN PROCESSED HTML
    }
}

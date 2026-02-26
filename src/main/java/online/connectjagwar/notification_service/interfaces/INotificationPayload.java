package online.connectjagwar.notification_service.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import online.connectjagwar.notification_service.notification_payloads.PaymentPayload;
import online.connectjagwar.notification_service.notification_payloads.WelcomePayload;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,             // <--- USE CORRESPONDING CLASS NAME
    include = JsonTypeInfo.As.PROPERTY,     // <--- LOOK FOR PAYLOAD TYPE == NAME 
    property = "payloadType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PaymentPayload.class, name="payment"),  // <---- payment refers to PaymentPayload CLASS
    @JsonSubTypes.Type(value = WelcomePayload.class, name="welcome")   // <---- payment refers to WelcomePayload CLASS
})
public interface INotificationPayload {
    // EMPTY OF ACCEPTING AS GENERIC PAYLOAD TYPE
}

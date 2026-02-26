package online.connectjagwar.notification_service.notification_payloads;

import online.connectjagwar.notification_service.interfaces.INotificationPayload;

public class PaymentPayload implements INotificationPayload {
    private String orderId;
    private double amount;
    private String productName;


    public PaymentPayload(){
        // EMPTY FOR JACKSON SERIALIZATION
    }

    public PaymentPayload(String orderId, double amount, String productName){
        this.orderId = orderId;
        this.amount = amount;
        this.productName = productName;
    }

    public String getOrderId(){
        return orderId;
    }
    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public double getAmount(){
        return amount;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }

    public String getProductName(){
        return productName;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
}

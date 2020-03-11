package br.com.zup.order.event;

public class PaymentFailedEvent {

    private String orderId;
    private String message;

    public PaymentFailedEvent() {
    }

    public PaymentFailedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

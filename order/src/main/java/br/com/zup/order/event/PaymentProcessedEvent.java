package br.com.zup.order.event;

public class PaymentProcessedEvent {

    private String orderId;

    public PaymentProcessedEvent() {
    }

    public PaymentProcessedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

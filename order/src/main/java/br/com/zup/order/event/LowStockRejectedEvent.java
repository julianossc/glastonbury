package br.com.zup.order.event;

public class LowStockRejectedEvent {


    private String orderId;
    private String message;

    public LowStockRejectedEvent() {
    }

    public LowStockRejectedEvent(String orderId) {
        this.orderId = orderId;
    }

    public LowStockRejectedEvent(String orderId, String message) {
        this.orderId = orderId;
        this.message = message;
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

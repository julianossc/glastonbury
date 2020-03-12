package br.com.zup.inventory.event;

public class LowStockRejectedEvent {

    public LowStockRejectedEvent(String orderId) {
        this.orderId = orderId;
    }

    private String orderId;
    private String message;

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

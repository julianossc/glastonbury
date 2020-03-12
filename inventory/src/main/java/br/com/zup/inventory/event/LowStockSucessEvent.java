package br.com.zup.inventory.event;

public class LowStockSucessEvent {

    public LowStockSucessEvent(String orderId) {
        this.orderId = orderId;
    }

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

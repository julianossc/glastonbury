package br.com.zup.payment.event;

public class LowStockSucessEvent {

    private String orderId;

    public LowStockSucessEvent() {
    }

    public LowStockSucessEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

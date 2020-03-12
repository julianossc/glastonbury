package br.com.zup.order.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEvent implements Serializable {

    private String orderId;
    private String customerId;
    private BigDecimal amount;
    private List<OrderItemEvent> items;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String customerId, BigDecimal amount, List<OrderItemEvent> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<OrderItemEvent> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEvent> items) {
        this.items = items;
    }
}

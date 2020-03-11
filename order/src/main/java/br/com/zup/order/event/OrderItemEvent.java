package br.com.zup.order.event;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemEvent implements Serializable {
    private String id;
    private String name;
    private BigDecimal amount;
    private Integer quantity;

    public OrderItemEvent(){ }

    public OrderItemEvent(String id, String name, BigDecimal amount, Integer quantity){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.quantity = quantity;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public BigDecimal getAmount(){
        return this.amount;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public Integer getQuantity(){
        return this.quantity;
    }
}

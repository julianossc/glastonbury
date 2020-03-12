package br.com.zup.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "inventory_item")
public class InventoryItem {

    @Id
    private String id;
    private String name;
    private Integer quantity;

    public void decreaseStockQuantity(Integer quantity){
        this.quantity -= quantity;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

package br.com.zup.inventory;

import br.com.zup.inventory.entity.InventoryItem;
import br.com.zup.inventory.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class InventoryApplication {

    private final InventoryItemRepository repository;

    @Autowired
    public InventoryApplication(InventoryItemRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @EventListener(value = ApplicationReadyEvent.class)
    public void execute(ApplicationReadyEvent event) {
        InventoryItem one = new InventoryItem();
        one.setId("3852cb18-9c19-4326-ac77-a1a0264bd98c");
        one.setName("day-one");
        one.setQuantity(30);

        repository.save(one);

        InventoryItem two = new InventoryItem();
        two.setId("3852cb18-9c19-4326-ac77-a1a0264bd98d");
        two.setName("day-two");
        two.setQuantity(10);

        repository.save(two);
    }
}

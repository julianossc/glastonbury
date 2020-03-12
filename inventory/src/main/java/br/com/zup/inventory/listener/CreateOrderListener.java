package br.com.zup.inventory.listener;

import br.com.zup.inventory.event.OrderCreatedEvent;
import br.com.zup.inventory.service.ProcessOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateOrderListener {

    private final ObjectMapper objectMapper;
    private final ProcessOrderService processOrderService;

    @Autowired
    public CreateOrderListener(ObjectMapper objectMapper, ProcessOrderService processOrderService) {
        this.objectMapper = objectMapper;
        this.processOrderService = processOrderService;
    }

    @KafkaListener(topics = "created-orders", groupId = "inventory-group-id")
    public void listen(String message) throws IOException {
        OrderCreatedEvent event = this.objectMapper.readValue(message, OrderCreatedEvent.class);
        processOrderService.execute(event);
    }
}

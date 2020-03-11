package br.com.zup.order.listener;

import br.com.zup.order.event.LowStockRejectedEvent;
import br.com.zup.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LowStockRejectedListener {

    private final ObjectMapper objectMapper;
    private final OrderService service;

    @Autowired
    public LowStockRejectedListener(ObjectMapper objectMapper, OrderService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }


    @KafkaListener(topics = "low-stock-rejected", groupId = "order-group-id")
    public void listen(String message) throws IOException {
        LowStockRejectedEvent event = this.objectMapper.readValue(message, LowStockRejectedEvent.class);
        service.changeOrderToFailed(event.getOrderId(), event.getMessage());
    }

}

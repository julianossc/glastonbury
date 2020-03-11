package br.com.zup.order.listener;

import br.com.zup.order.event.LowStockRejectedEvent;
import br.com.zup.order.event.PaymentProcessedEvent;
import br.com.zup.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PaymentProcessedListener {

    private final ObjectMapper objectMapper;
    private final OrderService service;

    @Autowired
    public PaymentProcessedListener(ObjectMapper objectMapper, OrderService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }


    @KafkaListener(topics = "payment-processed", groupId = "order-group-id")
    public void listen(String message) throws IOException {
        PaymentProcessedEvent event = this.objectMapper.readValue(message, PaymentProcessedEvent.class);
        service.changeToProcessed(event.getOrderId());
    }

}

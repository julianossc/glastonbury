package br.com.zup.order.listener;

import br.com.zup.order.event.LowStockRejectedEvent;
import br.com.zup.order.event.PaymentFailedEvent;
import br.com.zup.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PaymentFailedListener {

    private final ObjectMapper objectMapper;
    private final OrderService service;

    @Autowired
    public PaymentFailedListener(ObjectMapper objectMapper, OrderService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }


    @KafkaListener(topics = "payment-failed", groupId = "order-group-id")
    public void listen(String message) throws IOException {
        PaymentFailedEvent event = this.objectMapper.readValue(message, PaymentFailedEvent.class);
        service.changeOrderToFailed(event.getOrderId(), event.getMessage());
    }

}

package br.com.zup.payment.listener;

import br.com.zup.payment.event.LowStockSucessEvent;
import br.com.zup.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LowStockSucessListener {

    private final ObjectMapper objectMapper;
    private final PaymentService paymentService;

    @Autowired
    public LowStockSucessListener(ObjectMapper objectMapper, PaymentService paymentService) {
        this.objectMapper = objectMapper;
        this.paymentService = paymentService;
    }


    @KafkaListener(topics = "low-stock-sucess", groupId = "payment-group-id")
    public void listen(String message) throws IOException {
        LowStockSucessEvent event = this.objectMapper.readValue(message, LowStockSucessEvent.class);
        paymentService.process(event);
    }

}

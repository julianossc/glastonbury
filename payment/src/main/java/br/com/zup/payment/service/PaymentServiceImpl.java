package br.com.zup.payment.service;

import br.com.zup.payment.event.LowStockSucessEvent;
import br.com.zup.payment.event.PaymentFailedEvent;
import br.com.zup.payment.event.PaymentProcessedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final KafkaTemplate template;

    @Autowired
    public PaymentServiceImpl(KafkaTemplate template) {
        this.template = template;
    }

    @Override
    public void process(LowStockSucessEvent event) {

        //Success
        if(event.getOrderId().endsWith("c")){
            PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(event.getOrderId());

            Message<PaymentProcessedEvent> message = MessageBuilder.withPayload(paymentProcessedEvent)
                    .setHeader("Content-Type", "application/json")
                    .setHeader(KafkaHeaders.TOPIC, "payment-processed")
                    .setHeader(KafkaHeaders.PARTITION_ID, 0)
                    .build();

            System.out.println("PAGAMENTO APROVADO para ordem " + event.getOrderId());

            this.template.send(message);

        //Failed
        } else {
            PaymentFailedEvent paymentFailedEvent = new PaymentFailedEvent(event.getOrderId());
            paymentFailedEvent.setMessage("Não há limite");

            Message<PaymentFailedEvent> message = MessageBuilder.withPayload(paymentFailedEvent)
                    .setHeader("Content-Type", "application/json")
                    .setHeader(KafkaHeaders.TOPIC, "payment-failed")
                    .setHeader(KafkaHeaders.PARTITION_ID, 0)
                    .build();

            System.out.println("PAGAMENTO REJEITADO para ordem " + event.getOrderId());

            this.template.send(message);
        }
    }

}

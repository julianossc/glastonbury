package br.com.zup.inventory.service.impl;

import br.com.zup.inventory.entity.InventoryItem;
import br.com.zup.inventory.event.LowStockSucessEvent;
import br.com.zup.inventory.event.LowStockRejectedEvent;
import br.com.zup.inventory.event.OrderCreatedEvent;
import br.com.zup.inventory.repository.InventoryItemRepository;
import br.com.zup.inventory.service.ProcessOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.zup.inventory.event.OrderCreatedEvent.OrderItem;

@Service
public class ProcessOrderServiceImpl implements ProcessOrderService {

    private final InventoryItemRepository inventoryItemRepository;
    private final KafkaTemplate<String, LowStockSucessEvent> template;

    @Autowired
    public ProcessOrderServiceImpl(InventoryItemRepository inventoryItemRepository, KafkaTemplate<String, LowStockSucessEvent> template) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.template = template;
    }

    @Transactional
    public void execute(OrderCreatedEvent event) {
        List<InventoryItem> inventoryItems = new ArrayList<>();
        for(OrderItem orderItem : event.getItems()) {
            Optional<InventoryItem> optional = inventoryItemRepository.findByName(orderItem.getName());
            if (!optional.isPresent()) {
                LowStockRejectedEvent lowStockRejectedEvent = new LowStockRejectedEvent(event.getOrderId());
                lowStockRejectedEvent.setMessage("Não existe item com o id " + orderItem.getId());
                Message<LowStockRejectedEvent> message = MessageBuilder.withPayload(lowStockRejectedEvent)
                        .setHeader(KafkaHeaders.TOPIC, "low-stock-rejected")
                        .build();

                System.out.println("NÃO EXISTE ESTE INGRESSO COM ID " + orderItem.getId() + " ORDER REJEITADA");

                template.send(message);

                return;

            } else {
                InventoryItem inventoryItem = optional.get();
                if (orderItem.getQuantity() > inventoryItem.getQuantity()) {
                    LowStockRejectedEvent lowStockRejectedEvent = new LowStockRejectedEvent(event.getOrderId());
                    lowStockRejectedEvent.setMessage("Não estoque para item com o id " + orderItem.getId());
                    Message<LowStockRejectedEvent> message = MessageBuilder.withPayload(lowStockRejectedEvent)
                            .setHeader(KafkaHeaders.TOPIC, "low-stock-rejected")
                            .build();

                    System.out.println("NÃO HÁ INGRESSOS PARA ESTE DIA, ORDEM COM ID " + event.getOrderId() + " REJEITADA");
                    template.send(message);

                    return;

                } else {
                    inventoryItem.decreaseStockQuantity(orderItem.getQuantity());
                    inventoryItemRepository.save(inventoryItem);
                }
            }
        }

        System.out.println("FEITA A BAIXA PARA ORDEM COM ID " + event.getOrderId());

        LowStockSucessEvent lowStockSucessEvent = new LowStockSucessEvent(event.getOrderId());
        Message<LowStockSucessEvent> message = MessageBuilder.withPayload(lowStockSucessEvent)
                .setHeader(KafkaHeaders.TOPIC, "low-stock-sucess")
                .build();

        template.send(message);
    }
}
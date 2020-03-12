package br.com.zup.order.service.impl;

import br.com.zup.order.controller.request.CreateOrderRequest;
import br.com.zup.order.controller.response.OrderResponse;
import br.com.zup.order.entity.Order;
import br.com.zup.order.event.OrderCreatedEvent;
import br.com.zup.order.repository.OrderRepository;
import br.com.zup.order.service.OrderService;
import br.com.zup.order.service.translator.CreateOrderItemRequestToCreateOrderItemRequestTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private KafkaTemplate<String, OrderCreatedEvent> template;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, KafkaTemplate<String, OrderCreatedEvent> template) {
        this.orderRepository = orderRepository;
        this.template = template;
    }

    @Override
    public String save(CreateOrderRequest request) {
        String orderId = this.orderRepository.save(request.toEntity()).getId();

        OrderCreatedEvent event = new OrderCreatedEvent(
                orderId,
                request.getCustomerId(),
                request.getAmount(),
                request.getItems()
                        .stream()
                        .map(CreateOrderItemRequestToCreateOrderItemRequestTranslator::translate)
                        .collect(Collectors.toList())
        );

        Message<OrderCreatedEvent> message = MessageBuilder.withPayload(event)
                .setHeader("Content-Type", "application/json")
                .setHeader(KafkaHeaders.TOPIC, "created-orders")
                .setHeader(KafkaHeaders.PARTITION_ID, 0)
                .build();
        this.template.send(message);

        return orderId;
    }

    @Override
    public List<OrderResponse> findAll() {
        return this.orderRepository.findAll()
                .stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void changeOrderToFailed(String orderId, String reason) {
        System.out.println("ORDEM COM ID " + orderId + " REJEITADA, RAZÃO " + reason);

        Optional<Order> optional = orderRepository.findById(orderId);
        if(optional.isPresent()){
            Order order = optional.get();
            order.setStatus("failed");
            order.setFailedReason(reason);
            orderRepository.save(order);
        }
    }

    @Override
    public void changeToProcessed(String orderId) {
        System.out.println("ORDEM COM ID " + orderId + " FINALIZADA");

        Optional<Order> optional = orderRepository.findById(orderId);
        if(optional.isPresent()){
            Order order = optional.get();
            order.setStatus("processed");
            orderRepository.save(order);
        }
    }
}

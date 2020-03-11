package br.com.zup.order.service;

import br.com.zup.order.controller.request.CreateOrderRequest;
import br.com.zup.order.controller.response.OrderResponse;
import br.com.zup.order.event.LowStockRejectedEvent;
import br.com.zup.order.event.PaymentProcessedEvent;

import java.util.List;

public interface OrderService {

    String save(CreateOrderRequest request);

    List<OrderResponse> findAll();

    void changeOrderToFailed(String orderId, String reason);

    void changeToProcessed(String orderId);
}

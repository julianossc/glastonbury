package br.com.zup.order.service.translator;

import br.com.zup.order.controller.request.CreateOrderRequest;
import br.com.zup.order.event.OrderItemEvent;

public class CreateOrderItemRequestToCreateOrderItemRequestTranslator {

    public static OrderItemEvent translate(CreateOrderRequest.OrderItemPart orderItemPart ){
        return new OrderItemEvent(orderItemPart.getId(), orderItemPart.getName(), orderItemPart.getAmount(), orderItemPart.getQuantity());
    }

}

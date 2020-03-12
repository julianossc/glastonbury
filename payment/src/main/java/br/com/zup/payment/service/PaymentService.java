package br.com.zup.payment.service;

import br.com.zup.payment.event.LowStockSucessEvent;

public interface PaymentService {

    void process(LowStockSucessEvent event);

}

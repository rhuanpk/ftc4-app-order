package org.example.order.application.controllers.requests;

import java.math.BigDecimal;

public record PedidoCreateItemRequest(String produtoNome, BigDecimal valor, int quantidade) {

}

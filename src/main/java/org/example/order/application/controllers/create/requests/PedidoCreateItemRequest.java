package org.example.order.application.controllers.create.requests;

import java.math.BigDecimal;

public record PedidoCreateItemRequest(String produtoNome, BigDecimal valor, int quantidade) {

}

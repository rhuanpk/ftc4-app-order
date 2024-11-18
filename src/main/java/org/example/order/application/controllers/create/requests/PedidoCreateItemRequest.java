package org.example.order.application.controllers.create.requests;

public record PedidoCreateItemRequest(String produtoNome, double valor, int quantidade) {

}

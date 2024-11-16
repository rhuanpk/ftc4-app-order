package org.example.order.core.applications.usecases.criarPedido;

public record CriarPedidoItemInput(String produtoNome, double valor, int quantidade) {
}

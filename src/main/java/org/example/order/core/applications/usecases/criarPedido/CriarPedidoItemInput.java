package org.example.order.core.applications.usecases.criarPedido;

import java.math.BigDecimal;

public record CriarPedidoItemInput(String produtoNome, BigDecimal valor, int quantidade) {
}

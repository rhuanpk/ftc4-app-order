package org.example.order.core.applications.usecases.criarPedido;

import java.util.List;

public record CriarPedidoInput(String nomeCliente, List<CriarPedidoItemInput> items) {
}

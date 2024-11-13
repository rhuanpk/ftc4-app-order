package org.example.order.application.controllers.pedido.create.requests;

import java.util.UUID;

public record PedidoCreateItemRequest(UUID item_id, int quantidade) {
}

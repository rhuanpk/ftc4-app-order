package org.example.order.application.controllers.pedido.create.requests;

import java.util.List;
import java.util.UUID;

public record PedidoCreateRequest(UUID cliente_id, List<PedidoCreateItemRequest> items) {
}

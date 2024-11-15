package org.example.order.application.controllers.create.requests;

import java.util.List;

public record PedidoCreateRequest(String clienteNome, List<PedidoCreateItemRequest> items) {

}
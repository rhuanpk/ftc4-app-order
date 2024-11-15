package org.example.order.application.controllers.atualizarStatus.requests;

import org.example.order.core.domain.enums.StatusPedido;

public record AtualizarStatusPedidoRequest(StatusPedido statusPedido) {
}

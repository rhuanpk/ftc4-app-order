package org.example.order.application.controllers.pedido.atualizarStatus.requests;

import java.util.UUID;

public record AtualizarStatusPagamentoRequest(UUID id, StatusPagamento statusPagamento) {
}

package org.example.order.application.controllers.atualizarStatus.requests;

import org.example.order.core.domain.enums.StatusPagamento;

import java.util.UUID;

public record AtualizarStatusPagamentoRequest(UUID id, StatusPagamento statusPagamento) {
}

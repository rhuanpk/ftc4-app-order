package org.example.order.application.controllers.atualizarStatus.requests;

import java.util.UUID;

public record AtualizarStatusPagamentoRequest(UUID id, boolean pagamentoAprovado) {
}

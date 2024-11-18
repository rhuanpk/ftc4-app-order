package org.example.order.core.applications.usecases;

import org.example.order.core.applications.exception.EntityNotFoundException;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.enums.StatusPagamento;

import java.util.UUID;

public class AtualizarStatusPagamento {

    private final PedidoRepositoryInterface pedidoRepository;

    public AtualizarStatusPagamento(PedidoRepositoryInterface pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(UUID id, StatusPagamento statusPagamento) {
        Pedido pedido = this.pedidoRepository.getById(id);
        if (pedido == null) {
            throw new EntityNotFoundException("Pedido não encontrado");
        }
        pedido.setStatusPagamento(statusPagamento);
        return this.pedidoRepository.atualizarStatus(pedido);
    }

}

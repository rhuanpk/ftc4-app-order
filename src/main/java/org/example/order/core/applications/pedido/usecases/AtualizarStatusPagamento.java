package org.example.order.core.applications.pedido.usecases;

import java.util.UUID;

public class AtualizarStatusPagamento {

    private final PedidoRepositoryInterace pedidoRepository;

    public AtualizarStatusPagamento(PedidoRepositoryInterace pedidoRepository) {
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

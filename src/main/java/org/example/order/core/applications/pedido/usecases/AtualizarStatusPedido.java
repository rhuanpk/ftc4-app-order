package org.example.order.core.applications.pedido.usecases;

import java.util.UUID;

public class AtualizarStatusPedido {

    private final PedidoRepositoryInterace pedidoRepository;

    public AtualizarStatusPedido(PedidoRepositoryInterace pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(UUID id, StatusPedido statusPedido) {
        Pedido pedido = this.pedidoRepository.getById(id);
        if (pedido == null) {
            throw new EntityNotFoundException("Pedido não encontrado");
        }
        pedido.setStatus(statusPedido);
        return this.pedidoRepository.atualizarStatus(pedido);
    }

}

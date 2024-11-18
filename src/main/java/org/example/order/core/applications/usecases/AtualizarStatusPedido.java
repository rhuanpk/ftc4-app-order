package org.example.order.core.applications.usecases;

import org.example.order.core.applications.exception.EntityNotFoundException;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.enums.StatusPedido;

import java.util.UUID;

public class AtualizarStatusPedido {

    private final PedidoRepositoryInterface pedidoRepository;

    public AtualizarStatusPedido(PedidoRepositoryInterface pedidoRepository) {
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

package org.example.order.core.applications.usecases;

import org.example.order.core.applications.exception.EntityNotFoundException;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.domain.Pedido;

import java.util.UUID;

public class GetPedido {

    private final PedidoRepositoryInterface pedidoRepository;

    public GetPedido(PedidoRepositoryInterface pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(UUID id) {
        Pedido pedido = this.pedidoRepository.getById(id);
        if (pedido == null) {
            throw new EntityNotFoundException("Pedido não encontrado");
        }
        return pedido;
    }

}

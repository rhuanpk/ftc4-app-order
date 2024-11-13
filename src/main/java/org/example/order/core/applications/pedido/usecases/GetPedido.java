package org.example.order.core.applications.pedido.usecases;

import java.util.UUID;

public class GetPedido {

    private final PedidoRepositoryInterace pedidoRepository;

    public GetPedido(PedidoRepositoryInterace pedidoRepository) {
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

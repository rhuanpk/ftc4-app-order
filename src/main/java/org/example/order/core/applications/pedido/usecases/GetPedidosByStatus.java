package org.example.order.core.applications.pedido.usecases;

import java.util.List;

public class GetPedidosByStatus {

    private final PedidoRepositoryInterace pedidoRepository;

    public GetPedidosByStatus(PedidoRepositoryInterace pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> execute(StatusPedido statusPedido) {
        return this.pedidoRepository.listarPorStatus(statusPedido);
    }

}

package org.example.order.core.applications.usecases;

import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.enums.StatusPedido;

import java.util.List;

public class GetPedidosByStatus {

    private final PedidoRepositoryInterface pedidoRepository;

    public GetPedidosByStatus(PedidoRepositoryInterface pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> execute(StatusPedido statusPedido) {
        return this.pedidoRepository.listarPorStatus(statusPedido);
    }

}

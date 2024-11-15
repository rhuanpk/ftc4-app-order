package org.example.order.core.applications.usecases;

import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.domain.Pedido;

import java.util.List;

public class GetPedidos {

    private final PedidoRepositoryInterface pedidoRepository;

    public GetPedidos(PedidoRepositoryInterface pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> execute() {
        return this.pedidoRepository.listar();
    }

}

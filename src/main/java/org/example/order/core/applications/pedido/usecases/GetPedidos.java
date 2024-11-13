package org.example.order.core.applications.pedido.usecases;

import java.util.List;

public class GetPedidos {

    private final PedidoRepositoryInterace pedidoRepository;

    public GetPedidos(PedidoRepositoryInterace pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> execute() {
        return this.pedidoRepository.listar();
    }

}

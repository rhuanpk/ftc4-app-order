package org.example.order.core.applications.repositories;

import org.example.order.core.domain.Pedido;

import java.util.UUID;

public interface PedidoRepositoryInterface {

    public Pedido criarPedido(Pedido pedido);

//    public List<Pedido> listarPorStatus(StatusPedido statusPedido);
//
//    public List<Pedido> listar();
//
    public Pedido getById(UUID id);

    public Pedido atualizarStatus(Pedido pedido);

}

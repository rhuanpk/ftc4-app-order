package org.example.order.adapters.controllers;

import org.example.order.adapters.presenters.PedidoPresenter;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.applications.usecases.*;
import org.example.order.core.applications.usecases.criarPedido.CriarPedido;
import org.example.order.core.applications.usecases.criarPedido.CriarPedidoInput;
import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.enums.StatusPagamento;
import org.example.order.core.domain.enums.StatusPedido;

import java.util.List;
import java.util.UUID;

public class PedidoController {

    private final PedidoRepositoryInterface pedidoRepository;
//    private final ClienteRepositoryInterface clienteRepository;
//    private final ProdutoRepositoryInterface produtoRepository;

    public PedidoController(PedidoRepositoryInterface pedidoRepository/*, ClienteRepositoryInterface clienteRepository, ProdutoRepositoryInterface produtoRepository*/) {
        this.pedidoRepository = pedidoRepository;
//        this.clienteRepository = clienteRepository;
//        this.produtoRepository = produtoRepository;
    }

    public List<Object> listar() {
        GetPedidos getPedidos = new GetPedidos(this.pedidoRepository);
        List<Pedido> pedidos = getPedidos.execute();
        return PedidoPresenter.toList(pedidos);
    }

    public List<Object> listarPorStatus(StatusPedido statusPedido) {
        GetPedidosByStatus getPedidosByStatus = new GetPedidosByStatus(this.pedidoRepository);
        List<Pedido> pedidos = getPedidosByStatus.execute(statusPedido);
        return PedidoPresenter.toList(pedidos);
    }

    public Object criarPedido(CriarPedidoInput input) {
        CriarPedido criarPedido = new CriarPedido(this.pedidoRepository/*, this.clienteRepository, this.produtoRepository*/);
        return PedidoPresenter.toObject(criarPedido.execute(input));
    }

    public Object pagamentoAprovado(UUID id) {
        GetPedido getPedido = new GetPedido(this.pedidoRepository);
        return PedidoPresenter.toObjectStatusPedido(getPedido.execute(id));
    }

    public Object atualizarStatusPedido(UUID id, StatusPedido statusPedido) {
        AtualizarStatusPedido atualizarStatusPedido = new AtualizarStatusPedido(this.pedidoRepository);
        return atualizarStatusPedido.execute(id, statusPedido);
    }

    public Object atualizarStatusPagamento(UUID id, StatusPagamento statusPagamento) {
        AtualizarStatusPagamento atualizarStatusPagamento = new AtualizarStatusPagamento(this.pedidoRepository);
        return PedidoPresenter.toObjectStatusPedido(atualizarStatusPagamento.execute(id, statusPagamento));
    }

}

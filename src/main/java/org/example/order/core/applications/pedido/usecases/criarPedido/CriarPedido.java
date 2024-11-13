package org.example.order.core.applications.pedido.usecases.criarPedido;

import java.time.Instant;
import java.util.UUID;

public class CriarPedido {

    private final PedidoRepositoryInterace pedidoRepository;
    private final ClienteRepositoryInterface clienteRepository;
    private final ProdutoRepositoryInterface produtoRepository;

    public CriarPedido(PedidoRepositoryInterace pedidoRepository, ClienteRepositoryInterface clienteRepository, ProdutoRepositoryInterface produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido execute(CriarPedidoInput input) {
        Cliente cliente = this.clienteRepository.getClienteById(input.cliente_id());
        if (cliente == null) {
            throw new RegraDeNegocioException("Cliente não encontrado");
        }
        Pedido pedido = new Pedido(UUID.randomUUID(), input.cliente_id(), StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        for (CriarPedidoItemInput inputItem : input.items()) {
            Produto produto = this.produtoRepository.getById(inputItem.item_id());
            if (produto == null) {
                throw new RegraDeNegocioException("Item do pedido não encontrado");
            }
            pedido.addItem(new PedidoItem(inputItem.item_id(), produto.getPreco(), inputItem.quantidade()));
        }
        this.check(pedido);
        this.pedidoRepository.criarPedido(pedido);
        return pedido;
    }

    private void check(Pedido pedido) {
        if (pedido.getItems().isEmpty()) {
            throw new RegraDeNegocioException("O pedido deve contar um ou mais itens");
        }
        for (PedidoItem pedidoItem : pedido.getItems()) {
            if (pedidoItem.getQuantidade() < 1) {
                throw new RegraDeNegocioException("A quantidade do item deve ser maior que 0");
            }
        }
    }

}

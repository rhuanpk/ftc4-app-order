package org.example.order.core.applications.usecases.criarPedido;

import org.example.order.core.applications.exception.RegraDeNegocioException;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.PedidoItem;
import org.example.order.core.domain.enums.StatusPagamento;
import org.example.order.core.domain.enums.StatusPedido;

import java.time.Instant;
import java.util.UUID;

public class CriarPedido {

    private final PedidoRepositoryInterface pedidoRepository;

    public CriarPedido(PedidoRepositoryInterface pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(CriarPedidoInput input) {
        Pedido pedido = new Pedido(UUID.randomUUID(), input.nomeCliente(), StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        for (CriarPedidoItemInput inputItem : input.items()) {
            pedido.addItem(new PedidoItem(inputItem.produtoNome(), inputItem.valor(), inputItem.quantidade()));
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
            if (pedidoItem.getValor() < 0.01) {
                throw new RegraDeNegocioException("O valor do item deve ser maior que 0.00");
            }
            if (pedidoItem.getQuantidade() < 1) {
                throw new RegraDeNegocioException("A quantidade do item deve ser maior que 0");
            }
        }
    }

}

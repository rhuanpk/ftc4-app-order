package org.example.order.adapters.presenters;

import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.PedidoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoPresenter {

    public static Object toObject(Pedido pedido) {
        Map<String, Object> presenter = new HashMap<>();
        presenter.put("id", pedido.getId());
        presenter.put("valor", pedido.getValor());
        presenter.put("data_criacao", pedido.getDataCriacao());
        presenter.put("status_pedido", pedido.getStatusPedido());
        presenter.put("status_pagamento", pedido.getStatusPagamento());
        presenter.put("nome_cliente", pedido.getClienteNome());
        List<Map<String, Object>> produtosPresenter = new ArrayList<>();
        for (PedidoItem pedidoItem : pedido.getItems()) {
            Map<String, Object> produtoPresenter = new HashMap<>();
            produtoPresenter.put("quantidade", pedidoItem.getQuantidade());
            produtoPresenter.put("produto_nome", pedidoItem.getNomeProduto());
            produtoPresenter.put("valor", pedidoItem.getValor());
            produtosPresenter.add(produtoPresenter);
        }
        presenter.put("itens", produtosPresenter);
        return presenter;
    }

    public static List<Object> toList(List<Pedido> pedidos) {
        List<Object> presenters = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            presenters.add(PedidoPresenter.toObject(pedido));
        }
        return presenters;
    }

    public static Object toObjectStatusPedido(Pedido pedido) {
        Map<String, Object> presenter = new HashMap<>();
        presenter.put("id", pedido.getId());
        presenter.put("pagamento_aprovado", pedido.isPago());
        return presenter;
    }

}

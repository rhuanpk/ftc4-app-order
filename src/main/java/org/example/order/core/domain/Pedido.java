package org.example.order.core.domain;

import org.example.order.core.domain.enums.StatusPagamento;
import org.example.order.core.domain.enums.StatusPedido;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private UUID id;
    private String clienteNome;
    private StatusPedido statusPedido;
    private StatusPagamento statusPagamento;
    private Instant dataCriacao;
    private List<PedidoItem> items;

    public Pedido(UUID id, String clienteNome, StatusPedido statusPedido, StatusPagamento statusPagamento, Instant dataCriacao) {
        this.id = id;
        this.clienteNome = clienteNome;
        this.statusPedido = statusPedido;
        this.statusPagamento = statusPagamento;
        this.dataCriacao = dataCriacao;
        this.items = new ArrayList<>();
    }

    public UUID getId() {
        return this.id;
    }

    public String getClienteNome() {
        return this.clienteNome;
    }

    public StatusPedido getStatusPedido() {
        return this.statusPedido;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public List<PedidoItem> getItems() {
        return this.items;
    }

    public void addItem(PedidoItem pedidoItem) {
        this.items.add(pedidoItem);
    }

    public double getValor() {
        double valor = 0;
        for (PedidoItem pedidoItem : this.items) {
            valor += pedidoItem.getValorItem();
        }
        return valor;
    }

    public void setStatus(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public boolean isPago() {
        return this.statusPagamento == StatusPagamento.PAGO;
    }

}

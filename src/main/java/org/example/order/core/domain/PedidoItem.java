package org.example.order.core.domain;

import java.math.BigDecimal;

public class PedidoItem {

    private String nomeProduto;
    private BigDecimal valor;
    private int quantidade;

    public PedidoItem(String nomeProduto, BigDecimal valor, int quantidade) {
        this.nomeProduto = nomeProduto;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public String getNomeProduto() {
        return this.nomeProduto;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public BigDecimal getValorItem() {
        return this.valor.multiply(BigDecimal.valueOf(this.quantidade));
    }

}

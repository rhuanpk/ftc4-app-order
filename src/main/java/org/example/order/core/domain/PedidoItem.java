package org.example.order.core.domain;

import org.example.order.core.applications.exception.RegraDeNegocioException;

public class PedidoItem {

    private String nomeProduto;
    private double valor;
    private int quantidade;

    public PedidoItem(String nomeProduto, double valor, int quantidade) {
        this.nomeProduto = nomeProduto;
        if (valor < 0.01) {
            throw new RegraDeNegocioException("O valor do item deve ser maior que 0.00");
        }
        this.valor = valor;
        if (quantidade < 1) {
            throw new RegraDeNegocioException("A quantidade do item deve ser maior que 0");
        }
        this.quantidade = quantidade;
    }

    public String getNomeProduto() {
        return this.nomeProduto;
    }

    public double getValor() {
        return this.valor;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public double getValorItem() {
        return this.valor * this.quantidade;
    }

}

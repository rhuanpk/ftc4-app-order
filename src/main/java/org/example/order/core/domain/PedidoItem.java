package org.example.order.core.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class PedidoItem {

    private UUID itemId;
    private BigDecimal valor;
    private int quantidade;

    public PedidoItem(UUID itemId, BigDecimal valor, int quantidade) {
        this.itemId = itemId;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public UUID getItemId() {
        return this.itemId;
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

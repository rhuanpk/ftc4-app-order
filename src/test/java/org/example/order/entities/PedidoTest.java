package org.example.order.entities;

import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.PedidoItem;
import org.example.order.core.domain.enums.StatusPagamento;
import org.example.order.core.domain.enums.StatusPedido;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class PedidoTest {

    @ParameterizedTest
    @CsvSource({
            "AGUARDANDO, false",
            "PAGO, true"
    })
    void deveVerificarOMetodoIsPago(StatusPagamento statusPagamento, boolean pago) {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, statusPagamento, Instant.now());
        assertThat(pedido.isPago()).isEqualTo(pago);
    }

    @Test
    void deveCalcularOValorTotalDoPedido() {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto 1", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 20.00, 2));
        assertThat(pedido.getValor()).isEqualTo(50.00);
    }
}

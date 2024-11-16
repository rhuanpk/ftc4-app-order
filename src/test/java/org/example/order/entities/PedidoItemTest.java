package org.example.order.entities;

import org.example.order.core.applications.exception.RegraDeNegocioException;
import org.example.order.core.domain.PedidoItem;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class PedidoItemTest {

    @ParameterizedTest
    @CsvSource({
            "0.01, 2, 0.02",
            "10.23, 1, 10.23",
            "9.50, 3, 28.50"
    })
    void deveCalcularOValorToItem(double valor, int quantidade, double valorTotal) {
        PedidoItem pedidoItem = new PedidoItem("Produto", valor, quantidade);
        assertThat(pedidoItem.getValorItem()).isEqualTo(valorTotal);
    }

    @Test
    void naoDevePermitirCriarUmItemComValorZerado() {
        assertThatThrownBy(() -> new PedidoItem("Produto", 0.00, 1))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("O valor do item deve ser maior que 0.00");
    }

    @Test
    void naoDevePermitirCriarUmItemComQuantidadeZerada() {
        assertThatThrownBy(() -> new PedidoItem("Produto", 1.00, 0))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("A quantidade do item deve ser maior que 0");
    }

}

package org.example.order.repositories;

import org.example.order.application.driven.entities.PedidoEntity;
import org.example.order.application.driven.repositories.PedidoRepository;
import org.example.order.core.domain.enums.StatusPagamento;
import org.example.order.core.domain.enums.StatusPedido;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.util.UUID;

class PedidoRepositoryTest {

    @Mock
    private PedidoRepository pedidoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRegistrarUmPedido() {
        var pedido = PedidoEntity.builder()
                .id(UUID.randomUUID())
                .preco(10.00)
                .statusPedido(StatusPedido.RECEBIDO)
                .statusPagamento(StatusPagamento.AGUARDANDO)
                .build();

        when(this.pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedido);

        var pedidoSalvo = this.pedidoRepository.save(pedido);

        assertThat(pedidoSalvo).isNotNull().isEqualTo(pedido);
        verify(this.pedidoRepository, times(1)).save(pedido);
    }

}

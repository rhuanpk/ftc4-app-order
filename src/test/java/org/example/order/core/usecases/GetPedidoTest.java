package org.example.order.core.usecases;

import org.example.order.core.applications.exception.EntityNotFoundException;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.applications.usecases.AtualizarStatusPedido;
import org.example.order.core.applications.usecases.GetPedido;
import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.PedidoItem;
import org.example.order.core.domain.enums.StatusPagamento;
import org.example.order.core.domain.enums.StatusPedido;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.UUID;

class GetPedidoTest {

    @Mock
    private PedidoRepositoryInterface pedidoRepositoryInterface;

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
    void deveAtualizarOStatusDoPedido() {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        when(this.pedidoRepositoryInterface.getById(any(UUID.class))).thenReturn(pedido);

        GetPedido getPedido = new GetPedido(this.pedidoRepositoryInterface);
        Pedido pedidoEncontrado = getPedido.execute(pedido.getId());
        assertThat(pedidoEncontrado).isEqualTo(pedido);
        verify(this.pedidoRepositoryInterface, times(1)).getById(any(UUID.class));
    }

    @Test
    void naoDeveRetornarUmNaoEncontrado() {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        when(this.pedidoRepositoryInterface.getById(any(UUID.class))).thenReturn(null);

        GetPedido getPedido = new GetPedido(this.pedidoRepositoryInterface);
        assertThatThrownBy(() -> getPedido.execute(pedido.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pedido não encontrado");
        verify(this.pedidoRepositoryInterface, times(1)).getById(any(UUID.class));
    }

}

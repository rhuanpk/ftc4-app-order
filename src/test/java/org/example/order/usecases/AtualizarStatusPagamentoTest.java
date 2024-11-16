package org.example.order.usecases;

import org.example.order.core.applications.exception.EntityNotFoundException;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.applications.usecases.AtualizarStatusPagamento;
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

class AtualizarStatusPagamentoTest {

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
    void deveAtualizarOStatusDoPagamentoDoPedido() {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        when(this.pedidoRepositoryInterface.getById(any(UUID.class))).thenReturn(pedido);
        when(this.pedidoRepositoryInterface.atualizarStatus(any(Pedido.class))).thenReturn(pedido);

        AtualizarStatusPagamento atualizarStatusPagamento = new AtualizarStatusPagamento(this.pedidoRepositoryInterface);
        Pedido pedidoAtualizado = atualizarStatusPagamento.execute(pedido.getId(), StatusPagamento.PAGO);
        assertThat(pedidoAtualizado).isNotNull();
        assertThat(pedidoAtualizado.getStatusPagamento()).isEqualTo(StatusPagamento.PAGO);
        verify(this.pedidoRepositoryInterface, times(1)).getById(any(UUID.class));
        verify(this.pedidoRepositoryInterface, times(1)).atualizarStatus(any(Pedido.class));
    }

    @Test
    void naoDevePermitirMudarOStatusDePagamentoDeUmPedidoNaoEncontrado() {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        when(this.pedidoRepositoryInterface.getById(any(UUID.class))).thenReturn(null);

        AtualizarStatusPagamento atualizarStatusPagamento = new AtualizarStatusPagamento(this.pedidoRepositoryInterface);
        assertThatThrownBy(() -> atualizarStatusPagamento.execute(pedido.getId(), StatusPagamento.PAGO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pedido não encontrado");
        verify(this.pedidoRepositoryInterface, times(1)).getById(any(UUID.class));
    }

}

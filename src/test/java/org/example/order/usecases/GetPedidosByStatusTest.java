package org.example.order.usecases;

import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.applications.usecases.GetPedidosByStatus;
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
import java.util.List;
import java.util.UUID;

class GetPedidosByStatusTest {

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
        Pedido pedido2 = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        Pedido pedido3 = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.EM_PREPARACAO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        when(this.pedidoRepositoryInterface.listarPorStatus(any(StatusPedido.class)))
                .thenAnswer(invocation -> {
                    StatusPedido status = invocation.getArgument(0);
                    return List.of(pedido, pedido2, pedido3).stream().filter(p -> p.getStatusPedido() == status).toList();
                });

        GetPedidosByStatus getPedidosByStatus = new GetPedidosByStatus(this.pedidoRepositoryInterface);
        List<Pedido> pedidos = getPedidosByStatus.execute(StatusPedido.RECEBIDO);

        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos).contains(pedido, pedido2);
        assertThat(pedidos).doesNotContain(pedido3);
        verify(this.pedidoRepositoryInterface, times(1)).listarPorStatus(StatusPedido.RECEBIDO);
    }

}

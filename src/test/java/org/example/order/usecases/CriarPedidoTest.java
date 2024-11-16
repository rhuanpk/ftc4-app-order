package org.example.order.usecases;

import org.example.order.core.applications.exception.RegraDeNegocioException;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.applications.usecases.criarPedido.CriarPedido;
import org.example.order.core.applications.usecases.criarPedido.CriarPedidoInput;
import org.example.order.core.applications.usecases.criarPedido.CriarPedidoItemInput;
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
import java.util.ArrayList;
import java.util.UUID;

class CriarPedidoTest {

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
    void deveCriarUmPedido() {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        when(this.pedidoRepositoryInterface.criarPedido(any(Pedido.class))).thenReturn(pedido);

        CriarPedido criarPedido = new CriarPedido(this.pedidoRepositoryInterface);
        ArrayList<CriarPedidoItemInput> items = new ArrayList<>();
        items.add(new CriarPedidoItemInput("Produto", 10, 1));
        items.add(new CriarPedidoItemInput("Produto 2", 25, 1));
        CriarPedidoInput input = new CriarPedidoInput("Cliente", items);
        Pedido pedidoSalvo = criarPedido.execute(input);
        assertThat(pedidoSalvo).isNotNull();
        assertThat(pedidoSalvo.getId()).isNotNull();
        assertThat(pedidoSalvo.getClienteNome()).isEqualTo("Cliente");
        assertThat(pedidoSalvo.getStatusPedido()).isEqualTo(StatusPedido.RECEBIDO);
        assertThat(pedidoSalvo.getStatusPagamento()).isEqualTo(StatusPagamento.AGUARDANDO);
        verify(this.pedidoRepositoryInterface, times(1)).criarPedido(any(Pedido.class));
    }

    @Test
    void naoDevePermitirCriarPedidoSemItens() {
        CriarPedido criarPedido = new CriarPedido(this.pedidoRepositoryInterface);
        CriarPedidoInput input = new CriarPedidoInput("Cliente", new ArrayList<>());
        assertThatThrownBy(() -> criarPedido.execute(input))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("O pedido deve contar um ou mais itens");
    }

    @Test
    void naoDevePermitirCriarPedidoComQuantidadeDeItemMenorQueUm() {
        CriarPedido criarPedido = new CriarPedido(this.pedidoRepositoryInterface);
        ArrayList<CriarPedidoItemInput> items = new ArrayList<>();
        items.add(new CriarPedidoItemInput("Produto", 10.00, 0));
        CriarPedidoInput input = new CriarPedidoInput("Cliente", items);
        assertThatThrownBy(() -> criarPedido.execute(input))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("A quantidade do item deve ser maior que 0");
    }

    @Test
    void naoDevePermitirCriarPedidoComItemComValorZerado() {
        CriarPedido criarPedido = new CriarPedido(this.pedidoRepositoryInterface);
        ArrayList<CriarPedidoItemInput> items = new ArrayList<>();
        items.add(new CriarPedidoItemInput("Produto", 0.00, 1));
        CriarPedidoInput input = new CriarPedidoInput("Cliente", items);
        assertThatThrownBy(() -> criarPedido.execute(input))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("O valor do item deve ser maior que 0.00");
    }

}

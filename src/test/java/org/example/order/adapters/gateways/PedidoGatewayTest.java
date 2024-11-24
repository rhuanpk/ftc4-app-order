package org.example.order.adapters.gateways;

import org.example.order.application.driven.entities.PedidoEntity;
import org.example.order.application.driven.entities.PedidoItemEntity;
import org.example.order.application.driven.repositories.PedidoItemRepository;
import org.example.order.application.driven.repositories.PedidoRepository;
import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.PedidoItem;
import org.example.order.core.domain.enums.StatusPagamento;
import org.example.order.core.domain.enums.StatusPedido;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoGatewayTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoItemRepository pedidoItemRepository;

    @InjectMocks
    private PedidoGateway pedidoGateway;

    Pedido pedido;
    PedidoItem pedidoItem;

    PedidoEntity pedidoEntity;
    PedidoItemEntity pedidoItemEntity;

    UUID uuid;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);

        this.uuid = UUID.randomUUID();

        this.pedido = new Pedido(this.uuid, "Cliente A", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        this.pedidoItem = new PedidoItem("Produto A", 10.0, 1);
        this.pedido.addItem(this.pedidoItem);

        this.pedidoEntity = new PedidoEntity();
        this.pedidoEntity.setId(this.uuid);
        this.pedidoEntity.setCliente(this.pedido.getClienteNome());
        this.pedidoEntity.setStatusPedido(this.pedido.getStatusPedido());
        this.pedidoEntity.setStatusPagamento(this.pedido.getStatusPagamento());
        this.pedidoEntity.setDataCriacao(this.pedido.getDataCriacao());

        this.pedidoItemEntity = new PedidoItemEntity();
        this.pedidoItemEntity.setPedido(this.pedidoEntity);
        this.pedidoItemEntity.setProduto(this.pedidoItem.getNomeProduto());
        this.pedidoItemEntity.setPreco(this.pedidoItem.getValor());
        this.pedidoItemEntity.setQuantidade(this.pedidoItem.getQuantidade());

        List<PedidoItemEntity> itens = new ArrayList<>();
        itens.add(this.pedidoItemEntity);
        this.pedidoEntity.setItems(itens);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCriarUmPedido() {
        when(this.pedidoRepository.save(this.pedidoEntity)).thenReturn(this.pedidoEntity);
        when(this.pedidoItemRepository.save(this.pedidoItemEntity)).thenReturn(this.pedidoItemEntity);
        this.pedidoGateway.criarPedido(this.pedido);
        verify(this.pedidoRepository, times(1)).save(any(PedidoEntity.class));
        verify(this.pedidoItemRepository, times(1)).save(any(PedidoItemEntity.class));
    }

    @Test
    void deveRetornarUmPedidoPeloUuid() {
        when(this.pedidoRepository.findById(this.uuid)).thenReturn(Optional.of(this.pedidoEntity));
        Pedido pedido = this.pedidoGateway.getById(this.uuid);
        assertNotNull(pedido);
        assertEquals(pedido.getId(), this.pedidoEntity.getId());
        assertEquals(pedido.getClienteNome(), this.pedidoEntity.getCliente());
        assertEquals(pedido.getStatusPedido(), this.pedidoEntity.getStatusPedido());
        assertEquals(pedido.getStatusPagamento(), this.pedidoEntity.getStatusPagamento());
        assertEquals(pedido.getDataCriacao(), this.pedidoEntity.getDataCriacao());
        verify(this.pedidoRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void naoDeveRetornarUmPedidoPoisOUuidEhInvalido() {
        when(this.pedidoRepository.findById(this.uuid)).thenReturn(Optional.empty());
        Pedido pedido = this.pedidoGateway.getById(this.uuid);
        assertThat(pedido).isNull();
    }

}

package org.example.order.application.controllers.listar;

import org.example.order.application.controllers.create.CreatePedidoController;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.PedidoItem;
import org.example.order.core.domain.enums.StatusPagamento;
import org.example.order.core.domain.enums.StatusPedido;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ListarPedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoRepositoryInterface pedidoRepositoryInterface;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
        ListarPedidoController listarPedidoController = new ListarPedidoController(this.pedidoRepositoryInterface);
        this.mockMvc = MockMvcBuilders.standaloneSetup(listarPedidoController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveListarOsPedidos() throws Exception {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        Pedido pedido2 = new Pedido(UUID.randomUUID(), "Cliente 1", StatusPedido.PRONTO, StatusPagamento.PAGO, Instant.now());
        pedido2.addItem(new PedidoItem("Produto 11", 10.00, 2));
        pedido2.addItem(new PedidoItem("Produto 22", 25.00, 3));
        when(this.pedidoRepositoryInterface.listar()).thenReturn(List.of(pedido, pedido2));
        this.mockMvc.perform(
                        get("/pedidos")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome_cliente").value("Cliente"))
                .andExpect(jsonPath("$[0].status_pedido").value("RECEBIDO"))
                .andExpect(jsonPath("$[0].status_pagamento").value("AGUARDANDO"))
                .andExpect(jsonPath("$[0].data_criacao").isNotEmpty())
                .andExpect(jsonPath("$[0].valor").value(35.0))
                .andExpect(jsonPath("$[0].itens").isArray())
                .andExpect(jsonPath("$[0].itens.length()").value(2))
                .andExpect(jsonPath("$[0].itens[0].produto_nome").value("Produto"))
                .andExpect(jsonPath("$[0].itens[0].valor").value(10.0))
                .andExpect(jsonPath("$[0].itens[0].quantidade").value(1))
                .andExpect(jsonPath("$[0].itens[1].produto_nome").value("Produto 2"))
                .andExpect(jsonPath("$[0].itens[1].valor").value(25.0))
                .andExpect(jsonPath("$[0].itens[1].quantidade").value(1))
                .andExpect(jsonPath("$[1].nome_cliente").value("Cliente 1"))
                .andExpect(jsonPath("$[1].status_pedido").value("PRONTO"))
                .andExpect(jsonPath("$[1].status_pagamento").value("PAGO"))
                .andExpect(jsonPath("$[1].data_criacao").isNotEmpty())
                .andExpect(jsonPath("$[1].valor").value(95.0))
                .andExpect(jsonPath("$[1].itens").isArray())
                .andExpect(jsonPath("$[1].itens.length()").value(2))
                .andExpect(jsonPath("$[1].itens[0].produto_nome").value("Produto 11"))
                .andExpect(jsonPath("$[1].itens[0].valor").value(10.0))
                .andExpect(jsonPath("$[1].itens[0].quantidade").value(2))
                .andExpect(jsonPath("$[1].itens[1].produto_nome").value("Produto 22"))
                .andExpect(jsonPath("$[1].itens[1].valor").value(25.0))
                .andExpect(jsonPath("$[1].itens[1].quantidade").value(3));
        verify(this.pedidoRepositoryInterface, times(1)).listar();
    }

}

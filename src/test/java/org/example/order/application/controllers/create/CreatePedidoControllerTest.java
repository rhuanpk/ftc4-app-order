package org.example.order.application.controllers.create;

import org.example.order.adapters.services.RequestInterface;
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
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CreatePedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoRepositoryInterface pedidoRepositoryInterface;

    @Mock
    private RequestInterface requestInterface;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
        CreatePedidoController createPedidoController = new CreatePedidoController(this.pedidoRepositoryInterface, this.requestInterface);
        this.mockMvc = MockMvcBuilders.standaloneSetup(createPedidoController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarUmPedido() throws Exception {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        when(this.pedidoRepositoryInterface.criarPedido(any(Pedido.class))).thenReturn(pedido);
        this.mockMvc.perform(
                        post("/pedidos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"clienteNome\": \"Cliente\", \"items\": [{\"produtoNome\": \"Produto\", \"valor\": 10, \"quantidade\": 1}, {\"produtoNome\": \"Produto 2\", \"valor\": 25, \"quantidade\": 2}]}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome_cliente").value("Cliente"))
                .andExpect(jsonPath("$.status_pedido").value("RECEBIDO"))
                .andExpect(jsonPath("$.status_pagamento").value("AGUARDANDO"))
                .andExpect(jsonPath("$.data_criacao").isNotEmpty())
                .andExpect(jsonPath("$.valor").value(60.0))
                .andExpect(jsonPath("$.itens").isArray())
                .andExpect(jsonPath("$.itens[0].produto_nome").value("Produto"))
                .andExpect(jsonPath("$.itens[0].valor").value(10.0))
                .andExpect(jsonPath("$.itens[0].quantidade").value(1))
                .andExpect(jsonPath("$.itens[1].produto_nome").value("Produto 2"))
                .andExpect(jsonPath("$.itens[1].valor").value(25.0))
                .andExpect(jsonPath("$.itens[1].quantidade").value(2));
    }

}

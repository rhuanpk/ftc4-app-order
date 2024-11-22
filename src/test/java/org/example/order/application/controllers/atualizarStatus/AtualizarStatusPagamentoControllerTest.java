package org.example.order.application.controllers.atualizarStatus;

import org.example.order.application.controllers.create.CreatePedidoController;
import org.example.order.core.applications.exception.EntityNotFoundException;
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

public class AtualizarStatusPagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoRepositoryInterface pedidoRepositoryInterface;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
        AtualizarStatusPagamentoController atualizarStatusPagamentoController = new AtualizarStatusPagamentoController(this.pedidoRepositoryInterface);
        this.mockMvc = MockMvcBuilders.standaloneSetup(atualizarStatusPagamentoController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAtualizarOStatusDoPagamentoDoPedido() throws Exception {

        UUID uuid = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        Pedido pedido = new Pedido(uuid, "Cliente", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        when(this.pedidoRepositoryInterface.getById(any(UUID.class))).thenReturn(pedido);
        when(this.pedidoRepositoryInterface.atualizarStatus(any(Pedido.class))).thenReturn(pedido);
        this.mockMvc.perform(
                        post("/pagamentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\": \""+uuid.toString()+"\", \"pagamentoAprovado\": \"true\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(uuid.toString()));
    }

}

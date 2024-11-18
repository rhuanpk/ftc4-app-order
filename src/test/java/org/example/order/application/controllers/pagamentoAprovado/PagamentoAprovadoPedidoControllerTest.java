package org.example.order.application.controllers.pagamentoAprovado;

import org.example.order.application.controllers.atualizarStatus.AtualizarStatusPedidoController;
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

public class PagamentoAprovadoPedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoRepositoryInterface pedidoRepositoryInterface;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
        PagamentoAprovadoPedidoController pagamentoAprovadoPedidoController = new PagamentoAprovadoPedidoController(this.pedidoRepositoryInterface);
        this.mockMvc = MockMvcBuilders.standaloneSetup(pagamentoAprovadoPedidoController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAtualizarOStatusDoPedido() throws Exception {

        UUID uuid = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        Pedido pedido = new Pedido(uuid, "Cliente", StatusPedido.RECEBIDO, StatusPagamento.PAGO, Instant.now());
        pedido.addItem(new PedidoItem("Produto", 10.00, 1));
        pedido.addItem(new PedidoItem("Produto 2", 25.00, 1));
        when(this.pedidoRepositoryInterface.getById(any(UUID.class))).thenReturn(pedido);
        this.mockMvc.perform(
                        get("/pedidos/pagamento/" + uuid.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.pagamento_aprovado").value(true));
    }

}

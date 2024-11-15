package org.example.order.application.controllers.pagamentoAprovado;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.order.adapters.controllers.PedidoController;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class PagamentoAprovadoPedidoController {

    private final PedidoRepositoryInterface pedidoRepositoryInterface;

    @GetMapping("/pagamento/{id}")
    @Operation(tags = "Pedidos")
    public ResponseEntity<Object> pagamentoAprovado(@PathVariable("id") UUID id) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterface);
        return new ResponseEntity<>(pedidoController.pagamentoAprovado(id), HttpStatus.OK);
    }

}

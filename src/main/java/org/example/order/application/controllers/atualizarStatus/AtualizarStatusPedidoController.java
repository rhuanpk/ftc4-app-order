package org.example.order.application.controllers.atualizarStatus;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.order.adapters.controllers.PedidoController;
import org.example.order.application.controllers.atualizarStatus.requests.AtualizarStatusPedidoRequest;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class AtualizarStatusPedidoController {

    private final PedidoRepositoryInterface pedidoRepositoryInterface;

    @PutMapping("/{id}")
    @Operation(tags = "Pedidos")
    public ResponseEntity<Object> atualizarStatus(@PathVariable("id") UUID id, @RequestBody AtualizarStatusPedidoRequest request) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterface);
        var pedido = pedidoController.atualizarStatusPedido(id, request.statusPedido());
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

}

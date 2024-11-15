package org.example.order.application.controllers.listaStatus;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.order.adapters.controllers.PedidoController;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.domain.enums.StatusPedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class ListaPorStatusPedidoController {

    private final PedidoRepositoryInterface pedidoRepositoryInterface;

    @GetMapping("/{pedidoStatus}")
    @Operation(tags = "Pedidos")
    public ResponseEntity<List<Object>> listarPorStatus(@PathVariable("pedidoStatus") StatusPedido statusPedido) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterface);
        return new ResponseEntity<>(pedidoController.listarPorStatus(statusPedido), HttpStatus.OK);
    }

}

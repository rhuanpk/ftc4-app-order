package org.example.order.application.controllers.listar;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.order.adapters.controllers.PedidoController;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class ListarPedidoController {

    private final PedidoRepositoryInterface pedidoRepositoryInterface;

    @GetMapping
    @Operation(tags = "Pedidos")
    public ResponseEntity<List<Object>> listar() {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterface);
        return new ResponseEntity<>(pedidoController.listar(), HttpStatus.OK);
    }

}

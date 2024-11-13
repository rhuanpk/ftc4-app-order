package org.example.order.application.controllers.pedido.atualizarStatus;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class AtualizarStatusPedidoController {

    private final PedidoRepositoryInterace pedidoRepositoryInterace;
    private final ClienteRepositoryInterface clienteRepositoryInterface;
    private final ProdutoRepositoryInterface produtoRepositoryInterface;

    @PutMapping("/{id}")
    @Operation(tags = "Pedidos")
    public ResponseEntity<Object> atualizarStatus(@PathVariable("id") UUID id, @RequestBody AtualizarStatusPedidoRequest request) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterace, this.clienteRepositoryInterface, this.produtoRepositoryInterface);
        var pedido = pedidoController.atualizarStatusPedido(id, request.statusPedido());
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }


}

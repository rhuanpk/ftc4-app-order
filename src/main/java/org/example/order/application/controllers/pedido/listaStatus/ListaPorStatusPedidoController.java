package org.example.order.application.controllers.pedido.listaStatus;

import lombok.AllArgsConstructor;
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

    private final PedidoRepositoryInterace pedidoRepositoryInterace;
    private final ClienteRepositoryInterface clienteRepositoryInterface;
    private final ProdutoRepositoryInterface produtoRepositoryInterface;

    @GetMapping("/{pedidoStatus}")
    @Operation(tags = "Pedidos")
    public ResponseEntity<List<Object>> listarPorStatus(@PathVariable("pedidoStatus") StatusPedido statusPedido) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterace, this.clienteRepositoryInterface, this.produtoRepositoryInterface);
        return new ResponseEntity<>(pedidoController.listarPorStatus(statusPedido), HttpStatus.OK);
    }


}

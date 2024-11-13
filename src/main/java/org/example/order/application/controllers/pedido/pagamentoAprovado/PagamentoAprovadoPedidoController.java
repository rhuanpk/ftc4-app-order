package org.example.order.application.controllers.pedido.pagamentoAprovado;

import lombok.AllArgsConstructor;
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

    private final PedidoRepositoryInterace pedidoRepositoryInterace;
    private final ClienteRepositoryInterface clienteRepositoryInterface;
    private final ProdutoRepositoryInterface produtoRepositoryInterface;

    @GetMapping("/pagamento/{id}")
    @Operation(tags = "Pedidos")
    public ResponseEntity<Object> pagamentoAprovado(@PathVariable("id") UUID id) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterace, this.clienteRepositoryInterface, this.produtoRepositoryInterface);
        return new ResponseEntity<>(pedidoController.pagamentoAprovado(id), HttpStatus.OK);
    }

}
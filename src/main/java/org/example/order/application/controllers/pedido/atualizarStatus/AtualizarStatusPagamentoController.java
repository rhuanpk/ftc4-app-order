package org.example.order.application.controllers.pedido.atualizarStatus;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pagamentos")
@AllArgsConstructor
public class AtualizarStatusPagamentoController {

    private final PedidoRepositoryInterace pedidoRepositoryInterace;
    private final ClienteRepositoryInterface clienteRepositoryInterface;
    private final ProdutoRepositoryInterface produtoRepositoryInterface;

    @PostMapping
    @Operation(tags = "Pagamentos", summary = "Webhook de atualização do status do pagamento")
    public ResponseEntity<Object> atualizarStatusPagamento(@RequestBody AtualizarStatusPagamentoRequest request) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterace, this.clienteRepositoryInterface, this.produtoRepositoryInterface);
        var pedido = pedidoController.atualizarStatusPagamento(request.id(), request.statusPagamento());
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

}

package org.example.order.application.controllers.create;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.order.adapters.controllers.PedidoController;
import org.example.order.application.controllers.create.requests.PedidoCreateItemRequest;
import org.example.order.application.controllers.create.requests.PedidoCreateRequest;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.applications.usecases.criarPedido.CriarPedidoInput;
import org.example.order.core.applications.usecases.criarPedido.CriarPedidoItemInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class CreatePedidoController {

    private final PedidoRepositoryInterface pedidoRepositoryInterace;

    @PostMapping
    @Operation(tags = "Pedidos")
    public ResponseEntity<Object> create(@RequestBody PedidoCreateRequest request) {
        PedidoController pedidoController = new PedidoController(this.pedidoRepositoryInterace);
        List<CriarPedidoItemInput> inputItens = new ArrayList<>();
        for (PedidoCreateItemRequest itemPedido : request.items()) {
            inputItens.add(new CriarPedidoItemInput(itemPedido.produtoNome(), itemPedido.valor(), itemPedido.quantidade()));
        }
        CriarPedidoInput input = new CriarPedidoInput(request.clienteNome(), inputItens);
        return new ResponseEntity<>(pedidoController.criarPedido(input), HttpStatus.CREATED);
    }

}

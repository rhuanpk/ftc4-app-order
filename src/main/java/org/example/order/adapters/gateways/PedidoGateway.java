package org.example.order.adapters.gateways;

import lombok.AllArgsConstructor;
import org.example.order.application.driven.entities.PedidoEntity;
import org.example.order.application.driven.entities.PedidoItemEntity;
import org.example.order.application.driven.repositories.PedidoItemRepository;
import org.example.order.application.driven.repositories.PedidoRepository;
import org.example.order.core.applications.repositories.PedidoRepositoryInterface;
import org.example.order.core.domain.Pedido;
import org.example.order.core.domain.PedidoItem;
import org.example.order.core.domain.enums.StatusPedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PedidoGateway implements PedidoRepositoryInterface {

    private PedidoRepository pedidoRepository;

    private PedidoItemRepository pedidoItemRepository;

    private final ModelMapper modelMapper;

    @Override
    public Pedido criarPedido(Pedido pedido) {

//        ClienteEntity clienteEntity = new ClienteEntity();
//        clienteEntity.setId(pedido.getClienteId());

        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(pedido.getId());
        pedidoEntity.setStatusPedido(pedido.getStatusPedido());
        pedidoEntity.setStatusPagamento(pedido.getStatusPagamento());
        pedidoEntity.setPreco(pedido.getValor());
        pedidoEntity.setCliente(pedido.getClienteNome());
        this.pedidoRepository.save(pedidoEntity);

        for (PedidoItem pedidoItem : pedido.getItems()) {
//            ProdutoEntity produtoEntity = new ProdutoEntity();
//            produtoEntity.setId(pedidoItem.getItemId());

            PedidoItemEntity pedidoItemEntity = new PedidoItemEntity();
            pedidoItemEntity.setQuantidade(pedidoItem.getQuantidade());
            pedidoItemEntity.setPedido(pedidoEntity);
            pedidoItemEntity.setProduto(pedidoItem.getNomeProduto());
            pedidoItemEntity.setPreco(pedidoItem.getValor());
            this.pedidoItemRepository.save(pedidoItemEntity);
        }

        return pedido;
    }

    @Override
    public List<Pedido> listarPorStatus(StatusPedido statusPedido) {
        return this.listaDtoToEntidade(this.pedidoRepository.listarPorStatus(statusPedido));
    }

    @Override
    public List<Pedido> listar() {
        return this.listaDtoToEntidade(this.pedidoRepository.listar());
    }

    @Override
    public Pedido getById(UUID id) {
        Optional<PedidoEntity> entity = this.pedidoRepository.findById(id);
        if (entity.isEmpty()) {
            return null;
        }
        return this.dtoToEntidade(entity.get());
    }

    @Override
    public Pedido atualizarStatus(Pedido pedido) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(pedido.getId());
        pedidoEntity.setStatusPedido(pedido.getStatusPedido());
        pedidoEntity.setStatusPagamento(pedido.getStatusPagamento());
        pedidoEntity.setPreco(pedido.getValor());
        pedidoEntity.setCliente(pedido.getClienteNome());

//        ClienteEntity clienteEntity = new ClienteEntity();
//        clienteEntity.setId(pedido.getClienteId());
//        pedidoEntity.setCliente(clienteEntity);

        List<PedidoItemEntity> pedidoItemEntities = new ArrayList<>();
        for (PedidoItem pedidoItem : pedido.getItems()) {
//            ProdutoEntity produtoEntity = new ProdutoEntity();
//            produtoEntity.setId(pedidoItem.getItemId());

            PedidoItemEntity pedidoItemEntity = new PedidoItemEntity();
            pedidoItemEntity.setQuantidade(pedidoItem.getQuantidade());
            pedidoItemEntity.setPedido(pedidoEntity);
            pedidoItemEntity.setPreco(pedidoItem.getValor());
            pedidoItemEntity.setProduto(pedidoItem.getNomeProduto());
            pedidoItemEntities.add(pedidoItemEntity);
        }
        pedidoEntity.setItems(pedidoItemEntities);
        pedidoEntity = pedidoRepository.save(pedidoEntity);
        return this.dtoToEntidade(pedidoEntity);
    }

    private List<Pedido> listaDtoToEntidade(List<PedidoEntity> listaDePedidos) {
        List<Pedido> listaDePedidosDTO = new ArrayList<>();
        for (PedidoEntity pedidoEntity : listaDePedidos) {
            listaDePedidosDTO.add(this.dtoToEntidade(pedidoEntity));
        }
        return listaDePedidosDTO;
    }

    private Pedido dtoToEntidade(PedidoEntity pedidoEntity) {
        Pedido pedido = new Pedido(
                pedidoEntity.getId(),
                pedidoEntity.getCliente(),
                pedidoEntity.getStatusPedido(),
                pedidoEntity.getStatusPagamento(),
                pedidoEntity.getDataCriacao()
        );
        for (PedidoItemEntity pedidoItemEntity : pedidoEntity.getItems()) {
            PedidoItem pedidoItem = new PedidoItem(pedidoItemEntity.getProduto(),
                    pedidoItemEntity.getPreco(),
                    pedidoItemEntity.getQuantidade()
            );
            pedido.addItem(pedidoItem);
        }
        return pedido;
    }

}

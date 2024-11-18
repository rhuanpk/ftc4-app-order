package org.example.order.application.driven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.order.core.domain.enums.StatusPagamento;
import org.example.order.core.domain.enums.StatusPedido;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_PEDIDO")
public class PedidoEntity {

    @Id
    private UUID id;

    private double preco;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    private String cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoItemEntity> items;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant dataCriacao;

}

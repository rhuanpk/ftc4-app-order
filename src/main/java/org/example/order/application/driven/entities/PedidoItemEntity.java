package org.example.order.application.driven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_PEDIDO_ITEM")
public class PedidoItemEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private double preco;

    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;

    private String produto;
}

package org.example.order.application.driven.repositories;

import org.example.order.application.driven.entities.PedidoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoItemRepository extends JpaRepository<PedidoItemEntity, UUID> {

}

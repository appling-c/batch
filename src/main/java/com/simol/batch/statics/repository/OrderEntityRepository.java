package com.simol.batch.statics.repository;

import com.simol.batch.statics.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}

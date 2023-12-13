package com.simol.batch.statics.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Order {
    private Long id;
    private Long memberId;
    private String orderNumber;
    private OrderStatus status;
    private String orderName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

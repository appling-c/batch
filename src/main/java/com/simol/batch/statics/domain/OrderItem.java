package com.simol.batch.statics.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderItem {
    private Long id;
    private Long orderProductId;
    private OrderItemStatus status;
    private int ea;
    private int productPrice;
    private int productTotalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

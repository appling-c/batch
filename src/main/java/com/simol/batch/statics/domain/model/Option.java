package com.simol.batch.statics.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Option {
    private Long id;
    private String name;
    private int extraPrice;
    private int ea;
    private Long productId;
    private OptionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

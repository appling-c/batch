package com.simol.batch.statics.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Product {
    private Long id;
    private Long sellerId;
    private Long categoryId;
    private String mainTitle;
    private String mainExplanation;
    private String productMainExplanation;
    private String productSubExplanation;
    private int originPrice;
    private int price;
    private String purchaseInquiry;
    private String origin;
    private String producer;
    private String mainImage;
    private String image1;
    private String image2;
    private String image3;
    private Long viewCnt;
    private ProductStatus status;
    private int ea;
    private ProductType type;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

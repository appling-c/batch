package com.simol.batch.statics.repository;

import com.simol.batch.statics.domain.Product;
import com.simol.batch.statics.domain.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate applingJdbcTemplate;

    public List<Product> findAllById(List<Long> productIdList) {
        List<Product> productList = new LinkedList<>();

        List<String> productIdAsStringList = productIdList.stream().map(o -> String.valueOf(o)).collect(Collectors.toList());
        applingJdbcTemplate.query("select * from product where id in (" + String.join(",", productIdAsStringList) + ")", rs -> {
            while (rs.next()) {
                productList.add(
                        Product.builder()
                                .id(rs.getLong("id"))
                                .sellerId(rs.getLong("seller_id"))
                                .categoryId(rs.getLong("category_id"))
                                .mainTitle(rs.getString("main_title"))
                                .mainExplanation(rs.getString("main_explanation"))
                                .productMainExplanation(rs.getString("product_main_explanation"))
                                .productSubExplanation(rs.getString("product_sub_explanation"))
                                .originPrice(rs.getInt("origin_price"))
                                .price(rs.getInt("price"))
                                .purchaseInquiry(rs.getString("purchase_inquiry"))
                                .origin(rs.getString("origin"))
                                .producer(rs.getString("producer"))
                                .mainImage(rs.getString("main_image"))
                                .type(ProductType.valueOf(rs.getString("type")))
                                .image1(rs.getString("image1"))
                                .image2(rs.getString("image2"))
                                .image3(rs.getString("image3"))
                                .build()
                );
            }
        });

        return productList;
    }

}

package com.simol.batch.statics.repository;

import com.simol.batch.statics.domain.model.OrderItem;
import com.simol.batch.statics.domain.model.OrderItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {
    private final JdbcTemplate applingJdbcTemplate;

    public List<OrderItem> findAllByOrderId(List<Long> orderIdList) {
        List<OrderItem> orderItemList = new LinkedList<>();
        if(orderIdList.isEmpty()) {
            return orderItemList;
        }
        List<String> orderIdAsStringList = orderIdList.stream().map(o -> String.valueOf(o)).collect(Collectors.toList());
        applingJdbcTemplate.query("select * from order_item where status = 'ORDERED' and order_id in (" + String.join(",", orderIdAsStringList) + ")", rs -> {
                orderItemList.add(
                        OrderItem.builder()
                                .id(rs.getLong("order_item_id"))
                                .orderProductId(rs.getLong("order_product_id"))
                                .productPrice(rs.getInt("product_price"))
                                .productTotalPrice(rs.getInt("product_total_price"))
                                .ea(rs.getInt("ea"))
                                .status(OrderItemStatus.valueOf(rs.getString("status")))
                                .modifiedAt(rs.getTimestamp("modified_at").toLocalDateTime())
                                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                                .build()
                );
        });

        return orderItemList;
    }
}

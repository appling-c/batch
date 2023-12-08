package com.simol.batch.statics.repository;

import com.simol.batch.job.statics.domain.Order;
import com.simol.batch.job.statics.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final JdbcTemplate applingJdbcTemplate;

    public List<Order> findAllGtNow() {
        List<Order> orderList = new LinkedList<>();

        applingJdbcTemplate.query("select * from orders where status = 'ORDERED';", rs -> {
            orderList.add(
                    Order.builder()
                            .id(rs.getLong("order_id"))
                            .memberId(rs.getLong("member_id"))
                            .orderNumber(rs.getString("order_number"))
                            .orderName(rs.getString("order_name"))
                            .status(OrderStatus.valueOf(rs.getString("status")))
                            .modifiedAt(rs.getTimestamp("modified_at").toLocalDateTime())
                            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                            .build()
            );
        });

        return orderList;
    }

}

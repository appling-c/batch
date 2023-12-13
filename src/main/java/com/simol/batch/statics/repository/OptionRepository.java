package com.simol.batch.statics.repository;

import com.simol.batch.statics.domain.model.Option;
import com.simol.batch.statics.domain.model.OptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OptionRepository {
    private final JdbcTemplate applingJdbcTemplate;


    public List<Option> findAllByProduct(List<Long> optionProductIdList) {
        List<Option> optionList = new LinkedList<>();
        if(optionProductIdList.isEmpty()) {
            return optionList;
        }

        List<String> optionIdAsStringList = optionProductIdList.stream().map(o -> String.valueOf(o)).collect(Collectors.toList());
        applingJdbcTemplate.query("select * from options where product_id in (" + String.join(",", optionIdAsStringList) + ")", rs -> {
            optionList.add(
                    Option.builder()
                            .id(rs.getLong("option_id"))
                            .name(rs.getString("name"))
                            .extraPrice(rs.getInt("extra_price"))
                            .ea(rs.getInt("ea"))
                            .productId(rs.getLong("product_id"))
                            .status(OptionStatus.valueOf(rs.getString("status")))
                            .build()
            );
        });
        return optionList;
    }
}

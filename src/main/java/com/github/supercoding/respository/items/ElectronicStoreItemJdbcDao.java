package com.github.supercoding.respository.items;

import com.github.supercoding.respository.ElectronicStoreItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ElectronicStoreItemJdbcDao implements ElectronicStoreItemRepository {

    // 이것도 빈이 주입되있음
    private final JdbcTemplate jdbcTemplate;
    // 순서가 엉뚱해지면 일일이 확인해야된다 실수를 줄이자  *ItemEntityBuilder*
    static RowMapper<ItemEntity> itemEntityRowMapper = (((rs, rowNum) ->
            new ItemEntity.ItemEntityBuilder()
            .id(rs.getInt("id"))
            .name( rs.getNString("name"))
            .type( rs.getNString("type"))
            .price(rs.getInt("price"))
            .cpu(rs.getNString("cpu"))
            .capacity( rs.getNString("capacity"))
            .build()));

//            new ItemEntity.ItemEntityBuilder(
//                    rs.getInt("id"),
//                    rs.getNString("name"),
//                    rs.getNString("type"),
//                    rs.getInt("price"),
//                    rs.getNString("cpu"),
//                    rs.getNString("capacity"))));

// 이런 경우에는 롬복을 쓰지 않는다.
    public ElectronicStoreItemJdbcDao(@Qualifier("jdbcTemplate1") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ItemEntity> findAllItems() {
        return jdbcTemplate.query("SELECT * FROM item", itemEntityRowMapper);
    }

    @Override
    public Integer saveItem(ItemEntity itemEntity) {
        jdbcTemplate.update("INSERT INTO item(name, type, price, cpu, capacity) VALUES (?, ?, ?, ?, ?)",
                itemEntity.getName(), itemEntity.getType(), itemEntity.getPrice(),
                itemEntity.getCpu(), itemEntity.getCapacity());

        ItemEntity itemEntityFound = jdbcTemplate.queryForObject("SELECT * FROM item WHERE name = ?", itemEntityRowMapper, itemEntity.getName());
        return itemEntityFound.getId();
    }

    @Override
    public ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity) {
        jdbcTemplate.update("UPDATE item " +
                        "SET name = ?, type = ?, price = ?, cpu =?, capacity = ?" +
                        "WHERE id = ?",
                itemEntity.getName(), itemEntity.getType(), itemEntity.getPrice(),
                itemEntity.getCpu(), itemEntity.getCapacity(), idInt);

        return jdbcTemplate.queryForObject("SELECT * FROM item WHERE id = ?", itemEntityRowMapper, idInt);
    }

    @Override
    public void deleteItem(int idInt) {
        jdbcTemplate.update("DELETE FROM item WHERE id = ?", idInt);
    }

    @Override
    public ItemEntity findItemById(Integer idInt) {
        return jdbcTemplate.queryForObject("SELECT * FROM item WHERE id = ?", itemEntityRowMapper, idInt);
    }

    @Override
    public void updateItemStock(Integer itemId, Integer stock) {
        jdbcTemplate.update("UPDATE item " +
                " SET stock = ? " +
                " WHERE id = ? ", stock, itemId);
    }
}
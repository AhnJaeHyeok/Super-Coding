package com.github.supercoding.respository.items;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface ElectronicStoreItemJpaRepository extends JpaRepository<ItemEntity, Integer> {

    List<ItemEntity>findItemEntitiesByTypeIn(List<String>types);

    List<ItemEntity>findItemEntitiesByPriceLessThanEqualOrderByPriceAsc(Integer MaxValue);

    Page<ItemEntity> findAllByTypeIn(List<String>types, Pageable pageable);
}

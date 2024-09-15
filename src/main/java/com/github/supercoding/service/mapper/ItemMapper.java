package com.github.supercoding.service.mapper;

import com.github.supercoding.respository.items.ItemEntity;
import com.github.supercoding.web.dto.items.Item;
import com.github.supercoding.web.dto.items.ItemBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {
    //싱글톤
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    //메소드
    // 스펙 하위에 있는 cpu 와 capacity 찾기 왜냐고 새로운 객체로 빼서 이런식으로 따로 찾아줘야함
    @Mapping(target = "spec.cpu", source = "cpu")
    @Mapping(target = "spec.capacity", source = "capacity")
    Item itemEntityToItem(ItemEntity itemEntity);
    ItemEntity idAndItemBodyToItemEntity(Object o, ItemBody itemBody);
}

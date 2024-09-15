package com.github.supercoding.web.dto.items;

import com.github.supercoding.respository.items.ItemEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//빈 생성자 와 Getter 없이 롬복을 사용하여 처리
@NoArgsConstructor
@Getter//롬복 적극 활용 !
@Setter
@ToString
public class Item {


    @ApiModelProperty(name = "id", value = "Item Id", example = "1")private String id;

    @ApiModelProperty(name = "name", value = "Item 이름", example = "Dell XPS 15")private String name;

    @ApiModelProperty(name = "type", value = "Item 기기타입", example = "Laptop")private String type;

    @ApiModelProperty(name = "price", value = "Item 가격", example = "125000")private Integer price;

    private Spec spec;

    public Item(String id, ItemBody itemBody){
        this.id = id;
        this.name = itemBody.getName();
        this.type = itemBody.getType();
        this.price = itemBody.getPrice();
        this.spec = itemBody.getSpec();
    }

//    public Item(String id, String name, String type, Integer price, String cpu, String capacity) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.price = price;
//        this.spec = new Spec(cpu, capacity);
//    }
//-------------맵퍼------------------------
//    public Item(ItemEntity itemEntity){
//        this.id = itemEntity.getId().toString();
//        this.type = itemEntity.getType();
//        this.price = itemEntity.getPrice();
//        this.name = itemEntity.getName();
//        this.spec = new Spec(itemEntity.getCpu(), itemEntity.getCapacity());
//    }
}

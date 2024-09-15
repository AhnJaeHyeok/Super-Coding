package com.github.supercoding.web.dto.items;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor // 빈생성자
public class ItemBody {
    @ApiModelProperty(name = "name", value = "Item 이름", example = "Dell XPS 15")private String name;

    @ApiModelProperty(name = "type", value = "Item 기기타입", example = "Laptop")private String type;

    @ApiModelProperty(name = "price", value = "Item 가격", example = "125000")private Integer price;
    private Spec spec;
}
//    public ItemBody() {
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public Integer getPrice() {
//        return price;
//    }
//
//    public Spec getSpec() {
//        return spec;
//    }
//}

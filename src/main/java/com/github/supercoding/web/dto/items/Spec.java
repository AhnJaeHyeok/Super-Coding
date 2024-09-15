package com.github.supercoding.web.dto.items;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Spec {
    @ApiModelProperty(name = "cpu", value = "Item CPU", example = "Google Tensor") private String cpu;
    @ApiModelProperty(name = "capcity", value = "Item 용량 Spec", example = "25G") private String capacity;
}
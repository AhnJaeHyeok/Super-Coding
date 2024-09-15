package com.github.supercoding.respository.items;

import com.github.supercoding.respository.storeSales.StoreSales;
import com.github.supercoding.web.dto.items.ItemBody;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") // 필드 이름 넣기
@ToString
@Builder
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 50, unique = true)
    private String name;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)//한 스토어는 여러 아이템을 가질수있고 한 아이템은 한개의 스토어만 가질수 있다
    @JoinColumn(name = "store_id", nullable = true)
    private StoreSales storeSales;

    @Column(name = "stock", columnDefinition = "DEFAULT 0 CHECK(stock) >= 0")
    private Integer stock;

    @Column(name = "cpu", length = 30)
    private String cpu;

    @Column(name = "capacity", length = 30)
    private String capacity;

    public ItemEntity(Integer id, String name, String type, Integer price, String cpu, String capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.storeSales = null; // store id에서 변경
        this.stock = 0;
        this.cpu = cpu;
        this.capacity = capacity;
    }

    public Optional<StoreSales> getStoreSales() {
        return Optional.ofNullable(storeSales);
    }

    public void setItemBody(ItemBody itemBody) {
        this.name = itemBody.getName();
        this.type = itemBody.getType();
        this.price = itemBody.getPrice();
        this.cpu = itemBody.getSpec().getCpu();
        this.capacity = itemBody.getSpec().getCapacity();
    }
// --@AllArgsConstructor 사용해 처리--------------------------

//    public ItemEntity(Integer id, String name, String type, Integer price, Integer storeId, Integer stock, String cpu, String capacity) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.price = price;
//        this.storeId = storeId;
//        this.stock = stock;
//        this.cpu = cpu;
//        this.capacity = capacity;
//    }

// --@Getter @Setter 롬복을 사용해 처리-----------------
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Integer getStoreId() {
//        return storeId;
//    }
//
//    public void setStoreId(Integer storeId) {
//        this.storeId = storeId;
//    }
//
//    public Integer getStock() {
//        return stock;
//    }
//
//    public void setStock(Integer stock) {
//        this.stock = stock;
//    }
//
//    public Integer getPrice() {
//        return price;
//    }
//
//    public void setPrice(Integer price) {
//        this.price = price;
//    }
//
//    public String getCpu() {
//        return cpu;
//    }
//
//    public void setCpu(String cpu) {
//        this.cpu = cpu;
//    }
//
//    public String getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(String capacity) {
//        this.capacity = capacity;
//    }

//   -- @EqualsAndHashCode(of = "id") 사용해 처리-------

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof ItemEntity)) {
//            return false;
//        }
//
//        ItemEntity that = (ItemEntity) o;
//
//        return id.equals(that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }
}

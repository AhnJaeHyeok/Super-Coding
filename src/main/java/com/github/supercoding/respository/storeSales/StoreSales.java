package com.github.supercoding.respository.storeSales;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Builder
            // Entity 이름 명시
@Entity(name = "store_sales")
public class StoreSales {
    //이 어노테이션 조합은 해당 필드가 기본 키이며, 데이터베이스가 자동으로 그 값을 생성해 주는 것을 나타냅니다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "store_name", length = 30)
    private String storeName;

    @Column(name = "amount", nullable = false, columnDefinition = "DEFAULT 0 CHECK(amount) >= 0")
    private Integer amount;

//롬복사용--------------------@AllArgsConstructor-------------------------------
//    public StoreSales(Integer id, String storeName, Integer amount) {
//        this.id = id;
//        this.storeName = storeName;
//        this.amount = amount;
//    }

//롬복사용--------------------@Getter @Setter-------------------------
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getStoreName() {
//        return storeName;
//    }
//
//    public void setStoreName(String storeName) {
//        this.storeName = storeName;
//    }
//
//    public Integer getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Integer amount) {
//        this.amount = amount;
//    }

//롬복사용-------------------------@EqualsAndHashCode(of = "id")--------------------------
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof StoreSales)) {
//            return false;
//        }
//
//        StoreSales that = (StoreSales) o;
//
//        return id.equals(that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }
}

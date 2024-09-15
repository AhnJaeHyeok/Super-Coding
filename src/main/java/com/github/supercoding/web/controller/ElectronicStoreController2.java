////Spring Boot 애플리케이션에서 ElectronicStoreController라는 컨트롤러 클래스를 정의하여,
//// 전자 상점의 아이템 리스트를 관리하고 조회하는 간단한 REST API를 제공합니다.
//
//// 재가동 할시 포스트 없어짐 다시 빈리스트 부터 시작
//
//package com.github.supercoding.web.controller;
//
//import com.github.supercoding.respository.ElectronicStoreItemRepository;
//import com.github.supercoding.web.dto.Item;
//import com.github.supercoding.web.dto.ItemBody;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RestController// RestController의 주용도는 Json 형태로 객체 데이터를 반환하는 것입니다.
//@RequestMapping("/api")// 클래스 또는 메소드에 적용될 수 있으며, HTTP 메소드(GET, POST 등)와 URL 경로를 지정할 수 있습니다.
//
//public class ElectronicStoreController {
//
//    private ElectronicStoreItemRepository electronicStoreItemRepository;
//
//    public ElectronicStoreController(ElectronicStoreItemRepository electronicStoreItemRepository) {
//        this.electronicStoreItemRepository = electronicStoreItemRepository;
//    }
//
//    //items는 Item 객체들의 리스트를 저장하기 위한 필드입니다.
//// ArrayList로 초기화되어 있으며, 이를 통해 여러 개의 Item 객체를 저장할 수 있습니다.
//
//    private static int serialItemId = 1;
//
////    private List<Item> items = new ArrayList<>(); // 포맨에서 빈값나옴
//
//    private List<Item> items = new ArrayList<>(Arrays.asList(
//            new Item(String.valueOf(serialItemId++), "Apple iPhone 12 Pro Max", "Smartphone", 1490000, "A14 Bionic", "512GB"),
//
//            new Item(String.valueOf(serialItemId++), "Samsung Galaxy S21 Ultra", "Smartphone", 1690000, "Exynos 2100", "256GB"),
//
//            new Item(String.valueOf(serialItemId++), "Google Pixel 6 Pro", "Smartphone", 1290000, "Google Tensor", "128GB"),
//
//            new Item(String.valueOf(serialItemId++), "Dell XPS 15", "Laptop", 2290000, "Intel Core i9", "1TB SSD"),
//
//            new Item(String.valueOf(serialItemId++), "Sony Alpha 7 III", "Mirrorless Camera", 2590000, "BIONZ X", "No internal storage"),
//
//            new Item(String.valueOf(serialItemId++), "Microsoft Xbox Series X", "Gaming Console", 499000, "Custom AMD Zen 2", "1TB SSD")));
//
////    // 응답 Dto <2>
////    @GetMapping("/items") // HTTP GET 요청을 처리하기 위해 사용
////    public List<Item> findAllItem() { // '/items'로 GET 요청이 들어오면 findAllItem 메소드를 호출
//       // return items;
//
//    @GetMapping("/items")
//    public List<Item> findAllItem() {
//        return  electronicStoreItemRepository.findAllItems();
//    }
//    }
//
//    // 요청 Dto  <1> 새로운 아이템 등록 (POST)
//    @PostMapping("/items")
//    public String registerItem(@RequestBody ItemBody itemBody) {
//
//        Item newItem = new Item(serialItemId++, itemBody);
//        items.add(newItem); // 저장
//        return "ID: " + newItem.getId();
//
//    }
//
//    // <3> ID path로 아이템 조회 (GET)
//    @GetMapping("/items/{id}") // 경로 변수로 전달된 id 값을 기반으로 아이템을 검색
//    public Item findItemByPathId(@PathVariable String id) {
//        // items 리스트에서 해당 id와 일치하는 아이템을 찾음
//        Item itemFounded = items.stream()
//                .filter((item -> item.getId().equals(id))) // id가 일치하는 아이템 필터링
//                .findFirst() // 첫 번째로 일치하는 아이템 반환
//                .orElseThrow(() -> new RuntimeException()); // 일치하는 아이템이 없으면 예외 발생
//
//        return itemFounded;
//
//    }
//
//    // <4> 쿼리 파라미터로 ID로 아이템 조회(GET)
//    @GetMapping("/items-query")
//    public Item findItemByQueryId(@RequestParam("id") String id) {
//        Item itemFounded = items.stream()
//                                .filter((item -> item.getId().equals(id))) // id가 일치하는 아이템 필터링
//                                .findFirst() // 첫 번째로 일치하는 아이템 반환
//                                .orElseThrow(() -> new RuntimeException()); // 일치하는 아이템이 없으면 예외 발생
//
//        return itemFounded;
//    }
//
//    // <5> 여러 개의 ID로 아이템 조회 (GET)
//    @GetMapping("/items-queries")
//    public List<Item> findItemByQueryIds(@RequestParam("id") List<String> ids) {
//
//        Set<String> idSet = ids.stream().collect(Collectors.toSet());
//
//        List<Item> itemsFound = items.stream().filter((item -> idSet.contains(item.getId())))
//                                              .collect(Collectors.toList());
//
//        return itemsFound;
//    }
//    // <6> 아이템 삭제 (DELETE)
//    @DeleteMapping("/items/{id}")
//    public String deleteItemByPathId(@PathVariable String id) {
//        Item itemFounded = items.stream()
//                .filter((item -> item.getId().equals(id))) // id가 일치하는 아이템 필터링
//                .findFirst() // 첫 번째로 일치하는 아이템 반환
//                .orElseThrow(() -> new RuntimeException()); // 일치하는 아이템이 없으면 예외 발생
//
//        items.remove(itemFounded);
//
//        return "Object with id = " + itemFounded.getId() + " has been deleted";
//    }
//    // <7> 아이템 업데이트 (PUT)
//
//    @PutMapping("/items/{id}")
//    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody) {
//        Item itemFounded = items.stream()
//                .filter((item -> item.getId().equals(id))) // id가 일치하는 아이템 필터링
//                .findFirst() // 첫 번째로 일치하는 아이템 반환
//                .orElseThrow(() -> new RuntimeException()); // 일치하는 아이템이 없으면 예외 발생
//
//        items.remove(itemFounded);
//
//        Item itemUpdated = new Item(Integer.valueOf(id), itemBody);
//        items.add(itemUpdated);
//
//        return itemUpdated;
//    }
//}

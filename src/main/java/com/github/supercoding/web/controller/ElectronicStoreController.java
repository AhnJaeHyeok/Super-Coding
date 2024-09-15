package com.github.supercoding.web.controller;

import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.items.BuyOrder;
import com.github.supercoding.web.dto.items.Item;
import com.github.supercoding.web.dto.items.ItemBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
@Api
public class ElectronicStoreController {

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // 빈 주입하는 부분 , 빈생성자 같은 경우 final로 많이 정의 한번 들어가면 바꿀수 없는 키워드
    private final ElectronicStoreItemService electronicStoreItemService;

//생성자 없애고 롬복으로 리콰이얼드 어쩌구 ~
//    public ElectronicStoreController(ElectronicStoreItemService electronicStoreItemService) {
//        this.electronicStoreItemService = electronicStoreItemService;
//    }


    // 나가는 DTO
    @ApiOperation("모든 Items을 검색")
    @GetMapping("/items")
    public List<Item> findAllItem(){
        log.info("GET /items 요청이 들어왔습니다.");
        List<Item> items = electronicStoreItemService.findAllItem();
        log.info("GET /items 응답: " + items);
        return items;

    }

    // 들어오는 DTO
    @ApiOperation("단일 Item 등록")
    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody){
        Integer itemId = electronicStoreItemService.saveItem(itemBody);
        return "ID: " +  itemId;
    }

    @ApiOperation("단일 Item id로 검색")
    @GetMapping("/items/{id}")
    public Item findItemByPathId(//컨트롤러 메소드에 파라미터 검색하는게 api파람
            @ApiParam(name = "id", value = "itme ID", example = "1")
            @PathVariable String id){
        return electronicStoreItemService.findItemById(id);
    }
    @ApiOperation("단일 Item id로 검색(쿼리문)")
    @GetMapping("/items-query")
    public Item findItemByQueryId(
            @ApiParam(name = "id", value = "itme ID", example = "1")
            @RequestParam("id") String id){
        return electronicStoreItemService.findItemById(id);
    }
    @ApiOperation("여러 Item ids로 검색 (쿼리문)")
    @GetMapping("/items-queries")
    public List<Item> findItemByQueryIds(@ApiParam(name = "ids", value = "item IDs", example = "[1,2,3]") @RequestParam("id") List<String> ids){
        log.info("/items-queries 요청 ids: " + ids);
        List<Item> items = electronicStoreItemService.findItemsByIds(ids);
        log.info("/items-queries 응답: " + items);
        return items;

    }
    @ApiOperation("단일 Item id로 삭제")
    @DeleteMapping("/items/{id}")
    public String deleteItemByPathId(@ApiParam(name = "id", value = "item ID", example = "1")  @PathVariable String id){
        electronicStoreItemService.deleteItem(id);
        return "Object with id =" + id + "has been deleted";
    }


    @ApiOperation("단일 Item id 수정")
    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody){
        return electronicStoreItemService.updateItem(id, itemBody);
    }

    @ApiOperation("단일 Item 구매")
    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder){
        Integer orderItemNums = electronicStoreItemService.buyItems(buyOrder);
        return "요청하신 Item 중 " + orderItemNums + "개를 구매 하였습니다.";
    }
    @ApiOperation("여러 Item types 검색 (쿼리문)")
    @GetMapping("/items-types")
    public List<Item> findItemByTypes(
            @RequestParam("type") List<String> types) {
        log.info("/items-types 요청 ids: " + types);
        List<Item> items = electronicStoreItemService.findItemsByTypes(types);
        log.info("/items-types  응답: " + items);
        return items;
    }
    @ApiOperation("단일 Item id로 검색 (쿼리문)")
    @GetMapping("/items-prices")
    public List<Item> findItemByPrices(
            @RequestParam("max") Integer maxValue){
        return electronicStoreItemService.findItemsOrderByPrices(maxValue);
    }

    @ApiOperation("pagination 지원")
    @GetMapping("/items-page")
    public Page<Item> findItemsPagination(Pageable pageable){
        return electronicStoreItemService.findAllWithPageable(pageable);
    }
    @ApiOperation("pagination 지원 2")
    @GetMapping("/items-types-page")
    public Page<Item> findItemsPagination(@RequestParam("type")List<String>types,Pageable pageable){
        return electronicStoreItemService.findAllWithPageable(types, pageable);
    }
}
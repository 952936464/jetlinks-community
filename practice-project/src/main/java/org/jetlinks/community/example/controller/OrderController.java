package org.jetlinks.community.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.service.ReactiveCrudService;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.jetlinks.community.example.entity.OrderEntity;
import org.jetlinks.community.example.entity.ProductEntity;
import org.jetlinks.community.example.enums.OrderStatus;
import org.jetlinks.community.example.enums.OrderType;

import org.jetlinks.community.example.enums.ProductType;
import org.jetlinks.community.example.service.OrderService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/order/crud")
@AllArgsConstructor
@Resource(id = "example", name = "增删改查演示")//资源定义,用于权限控制
@Tag(name = "order增删改查演示") //swagger
public class OrderController implements ReactiveServiceCrudController<OrderEntity, String> {

    private final OrderService orderService;
    private final ReactiveRepository<OrderEntity, String> orderRepository;

    private final QueryHelper queryHelper;

    //增
    @PostMapping("/_practice/_add")
    @Operation(summary = "增加订单订单号根据流水号和商品id决定")
    public Mono<Integer> addOrderEntity(@RequestBody OrderEntity orderEntity) {
        String seq = UUID.randomUUID().toString().replaceAll("-", "");
        String productId = orderEntity.getProductId();
        String s = OrderEntity.generateOrderId(seq, productId);
        orderEntity.setId(s);
        orderEntity.setOrderSerialNumber(seq);
        return orderService.insert(orderEntity);
    }


    @GetMapping("/_practice/_detail/_query")
    @Operation(summary = "根据请求体查询订单所有数据以及商品名称类型")
    public Mono<PagerResult<OrderInfo>> queryByParamEntity(QueryParamEntity queryParam){
        return queryHelper
            .select(OrderInfo.class)
            .all(OrderEntity.class)
            .as(ProductEntity::getProductType, OrderInfo::setProductType)
            .as(ProductEntity::getName, OrderInfo::setProductName)
            .from(OrderEntity.class)
            .leftJoin(ProductEntity.class, spec -> spec.is(OrderEntity::getProductId, ProductEntity::getId))
            .where(queryParam)
            .fetchPaged();
    }

    @Override
    public ReactiveCrudService<OrderEntity, String> getService() {
        return orderService;
    }


    @Getter
    @Setter
    public static class OrderInfo{
        private String id;

        private String productId;

        private String productName;

        private ProductType productType;

        private String orderId;

        private  String orderSerialNumber;

        private OrderType orderType;

        private OrderStatus orderStatus;

        private String creatorId;

        private String modifierId;

        private Long modifyTime;

        private Long createTime;
    }


}

package org.jetlinks.community.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.service.ReactiveCrudService;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.jetlinks.community.example.entity.Order;
import org.jetlinks.community.example.entity.OrderExtended;
import org.jetlinks.community.example.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order/crud")
@AllArgsConstructor
@Resource(id = "example", name = "增删改查演示")//资源定义,用于权限控制
@Tag(name = "增删改查演示") //swagger
public class OrderController implements ReactiveServiceCrudController<Order, String> {

    private final OrderService service;

    private final QueryHelper queryHelper;

    @GetMapping("/_detail/_query_native")
    public Mono<PagerResult<OrderInfo>> nativeJoinExample(QueryParamEntity query) {
        return queryHelper
            .select("select o.*, p.id, p.name, p.productType from order as o " +
                "left join product as p on o.productId = p.id", OrderInfo::new)
            .where(query)
            .fetchPaged();
    }

    @Override
    public ReactiveCrudService<Order, String> getService() {
        return service;
    }

    @Getter
    @Setter
    public static class OrderInfo {
        private Order order;

        private OrderExtended ext;
    }



}

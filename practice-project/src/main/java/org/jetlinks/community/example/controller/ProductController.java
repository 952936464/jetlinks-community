package org.jetlinks.community.example.controller;

import groovyjarjarpicocli.CommandLine;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.hswebframework.web.validator.ValidatorUtils;
import org.jetlinks.community.example.entity.OrderEntity;
import org.jetlinks.community.example.entity.ProductEntity;
import org.jetlinks.community.example.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/product/crud")
@AllArgsConstructor
@Getter
@Resource(id = "example", name = "增删改查演示")//资源定义,用于权限控制
@Tag(name = "product增删改查演示") //swagger
public class ProductController implements ReactiveServiceCrudController<ProductEntity, String> {
    private final ProductService service;

    private final QueryHelper queryHelper;
    private final ReactiveRepository<ProductEntity, String> productRepository;



    @GetMapping("/_practice/_detail/query/{productBatch}")
    @Operation(summary = "根据商品批次查询关联的订单的数量接口")
    public Mono<Integer> countByProductBatch(@PathVariable String productBatch) {
        return queryHelper
            .select(OrderInfo.class)
            .as(OrderEntity::getId,OrderInfo::setId)
            .from(OrderEntity.class)
            .leftJoin(ProductEntity.class, spec -> spec.is(OrderEntity::getProductId, ProductEntity::getId))
            .where(dsl -> dsl.is(ProductEntity::getProductBatch,productBatch))
            .count();
    }

    @Getter
    @Setter
    public static class OrderInfo{
        private String id;
    }

}

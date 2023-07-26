package org.jetlinks.community.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.jetlinks.community.example.entity.ProductEntity;
import org.jetlinks.community.example.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product/crud")
@AllArgsConstructor
@Getter
@Resource(id = "example", name = "增删改查演示")//资源定义,用于权限控制
@Tag(name = "增删改查演示") //swagger
public class ProductController implements ReactiveServiceCrudController<ProductEntity, String> {
    private final ProductService service;

    private final QueryHelper queryHelper;
    private final ReactiveRepository<ProductEntity, String> productRepository;

    //增
    @PostMapping("/_practice/_add")
    public Mono<Void> addProductEntity(@RequestBody Flux<ProductEntity> productEntityFlux) {
        return productRepository
            .save(productEntityFlux)
            .then();
    }
    //删
    @DeleteMapping("/_practice/{id}")
    public Mono<Integer> deleteProductEntityById(@PathVariable String id){
        return productRepository.deleteById(id);
    }
    //查
    @GetMapping("/_practice/{id}")
    public Mono<ProductEntity> queryProductEntityById(@PathVariable String id){
        return productRepository.findById(id);
    }

    //根据商品批次查询关联的订单的数量接口
    @GetMapping("/_practice/_detail/query/{productBatch}")
    public Mono<Integer> countByProductBatch(@PathVariable String productBatch) {
        return queryHelper
            .select("select * from (select o.id, p.product_Batch from s_order as o " +
            "left join s_product as p on o.product_Id = p.id) as T where T.product_batch = ?"
            , productBatch)
            .count();
    }
}

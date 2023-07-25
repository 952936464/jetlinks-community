package org.jetlinks.community.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.jetlinks.community.example.entity.Product;
import org.jetlinks.community.example.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product/crud")
@AllArgsConstructor
@Getter
@Resource(id = "example", name = "增删改查演示")//资源定义,用于权限控制
@Tag(name = "增删改查演示") //swagger
public class ProductController implements ReactiveServiceCrudController<Product, String> {
    private final ProductService service;

    private final QueryHelper queryHelper;

    /**@GetMapping("/_detail/_query")
    public Mono<PagerResult<CommodityManagementInfo>> joinExample(QueryParamEntity query) {
        return queryHelper
            .select(CommodityManagementInfo.class)
            .all(Product.class)
            .from(ExampleEntity.class)
            .leftJoin(ProductExtended.class, spec -> spec.is(ProductExtended::getExampleId, ExampleEntity::getId))
            //根据前端的动态条件参数自动构造查询条件以及分页排序等信息
            .where(query)
            .fetchPaged();
    }*/
    @GetMapping("/_detail/_query_native")
    public Mono<PagerResult<ProductInfo>> nativeJoinExample(QueryParamEntity query) {
        return queryHelper
            .select("select count(*) from (select o.id, p.productBatch from order as o " +
                    "left join product as p on o.productId = p.id) as T where T.batch = ?????query.batch"
                    ,
                ProductInfo::new)
            //根据前端的动态条件参数自动构造查询条件以及分页排序等信息
            .where(query)
            .fetchPaged();
    }

    @Getter
    @Setter
    public static class ProductInfo {
        private int count;
    }
}

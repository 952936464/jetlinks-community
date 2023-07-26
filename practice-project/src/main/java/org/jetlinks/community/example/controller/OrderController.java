package org.jetlinks.community.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.ezorm.rdb.mapping.defaults.record.Record;
import org.hswebframework.utils.file.FileUtils;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.service.ReactiveCrudService;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.jetlinks.community.example.entity.OrderEntity;
import org.jetlinks.community.example.entity.OrderExtended;
import org.jetlinks.community.example.entity.ProductEntity;
import org.jetlinks.community.example.enums.OrderType;

import org.jetlinks.community.example.service.OrderService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.persistence.criteria.Order;

@RestController
@RequestMapping("/order/crud")
@AllArgsConstructor
@Resource(id = "example", name = "增删改查演示")//资源定义,用于权限控制
@Tag(name = "增删改查演示") //swagger
public class OrderController implements ReactiveServiceCrudController<OrderEntity, String> {

    private final OrderService orderService;
    private final ReactiveRepository<OrderEntity, String> orderRepository;

    private final QueryHelper queryHelper;

    //增 todo

    //删
    @DeleteMapping("/_practice/{id}")
    public Mono<Integer> deleteOrderEntityById(@PathVariable String id){
        return orderRepository.deleteById(id);
    }
    //查
    @GetMapping("/_practice/{id}")
    public Mono<OrderEntity> queryOrderEntityById(@PathVariable String id){
        return orderRepository.findById(id);
    }


    //根据订单状态查询符合的订单详情：包含订单表所有字段，及商品id对应的商品名称及商品分类
    @GetMapping("/_practice/_detail/_query/{orderStatus}")
    public Mono<PagerResult<Record>> queryByOrderStatus(@PathVariable String orderStatus) {
        return queryHelper
            .select("select o.*, p.id, p.name, p.product_type from s_order as o " +
            "left join s_product as p on o.product_Id = p.id where o.order_status = ? ", orderStatus)
            .fetchPaged();
    }

    @Override
    public ReactiveCrudService<OrderEntity, String> getService() {
        return orderService;
    }

    @Getter
    @Setter
    public static class OrderInfo {
        private OrderEntity order;
        private OrderExtended ext;
    }

}

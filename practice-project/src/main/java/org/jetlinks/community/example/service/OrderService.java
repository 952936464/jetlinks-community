package org.jetlinks.community.example.service;

import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.jetlinks.community.example.entity.ProductEntity;
import org.jetlinks.community.example.entity.OrderEntity;
import org.jetlinks.community.example.enums.OrderType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class OrderService extends GenericReactiveCrudService<OrderEntity,String> {
    //暂时还没用到
    public Mono<Integer> updateStatus(String id, OrderType status) {
        return createUpdate()
            .set(OrderEntity::getOrderType, status)
            .where(ProductEntity::getId, status)
            .execute();
    }
}


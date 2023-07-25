package org.jetlinks.community.example.service;

import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.jetlinks.community.example.entity.Product;
import org.jetlinks.community.example.entity.Order;
import org.jetlinks.community.example.enums.OrderType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class OrderService extends GenericReactiveCrudService<Order,String> {
    public Flux<Order> findByName(String name) {
        return createQuery()
            .where(Order::getName, Objects.requireNonNull(name))
            .fetch();
    }
    public Mono<Integer> updateStatus(String id, OrderType status) {
        return createUpdate()
            .set(Order::getOrderType, status)
            .where(Product::getId, status)
            .execute();
    }
}

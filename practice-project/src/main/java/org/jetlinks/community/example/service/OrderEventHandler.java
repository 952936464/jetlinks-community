package org.jetlinks.community.example.service;

import javassist.expr.NewArray;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.web.api.crud.entity.Entity;
import org.hswebframework.web.crud.events.EntityCreatedEvent;
import org.hswebframework.web.crud.events.EntitySavedEvent;
import org.jetlinks.community.example.entity.OrderEntity;
import org.jetlinks.community.example.entity.ProductEntity;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventHandler {

    private final ProductService productService;


    //在订单新增时验证其商品id在数据库商品表中是否存在，不存在则发出报错
    @EventListener
    public void handleSaveEvent(EntityCreatedEvent<OrderEntity> event) {
        event.async(
            productService
                .createQuery()
                .in(ProductEntity::getId, event
                    .getEntity()
                    .stream()
                    .map(OrderEntity::getProductId)
                    .collect(Collectors.toList()))
                .fetch()
                .switchIfEmpty(Mono.error(new Throwable("商品不能为空!")))
        );
    }
}

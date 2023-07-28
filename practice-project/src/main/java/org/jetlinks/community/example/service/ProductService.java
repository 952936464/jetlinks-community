package org.jetlinks.community.example.service;

import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.jetlinks.community.example.entity.ProductEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;

//继承 GenericReactiveCrudService 解锁通用增删改查能力。
@Service
public class ProductService extends GenericReactiveCrudService<ProductEntity,String> {
    //查询
    public Flux<ProductEntity> findByName(String name) {
        return createQuery()
            .where(ProductEntity::getName, Objects.requireNonNull(name))
            .fetch();
    }
}

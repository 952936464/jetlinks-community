package org.jetlinks.community.example.service;

import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.jetlinks.community.example.entity.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;

//继承 GenericReactiveCrudService 解锁通用增删改查能力。
@Service
public class ProductService extends GenericReactiveCrudService<Product,String> {
    //查询
    public Flux<Product> findByName(String name) {
        return createQuery()
            .where(Product::getName, Objects.requireNonNull(name))
            .fetch();
    }

}

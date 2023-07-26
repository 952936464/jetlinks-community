package org.jetlinks.community.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.GenericEntity;
import org.hswebframework.web.crud.annotation.EnableEntityEvent;
import org.jetlinks.community.example.enums.ProductType;


import javax.persistence.Column;



@Getter
@Setter
public class OrderExtended{


    private String id;


    private String name;


    private ProductType productType;
}

package org.jetlinks.community.example.enums;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.Dict;
import org.hswebframework.web.dict.EnumDict;
import org.hswebframework.web.dict.I18nEnumDict;

@Getter
@AllArgsConstructor
@Dict("product-type")
public enum ProductType implements I18nEnumDict<String> {
    product1("商品种类1"),
    product2("商品种类2"),
    product3("商品种类3")
    ;

    private final String text;
    @Override
    public String getValue() {
        return name();
    }

}

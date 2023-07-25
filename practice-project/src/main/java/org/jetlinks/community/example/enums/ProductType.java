package org.jetlinks.community.example.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.I18nEnumDict;

@Getter
@AllArgsConstructor
public enum ProductType implements I18nEnumDict<String> {
    commodity001("商品种类1"),
    commodity002("商品种类2"),
    commodity003("商品种类3")
    ;

    private final String text;
    @Override
    public String getValue() {
        return name();
    }

}

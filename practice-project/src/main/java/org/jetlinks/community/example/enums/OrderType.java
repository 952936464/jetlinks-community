package org.jetlinks.community.example.enums;

;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;

import lombok.Getter;
import org.hswebframework.web.dict.Dict;
import org.hswebframework.web.dict.EnumDict;
import org.hswebframework.web.dict.I18nEnumDict;


@Getter
@AllArgsConstructor
@Dict("order-type")
@JsonDeserialize(contentUsing = EnumDict.EnumDictJSONDeserializer.class)
public enum OrderType implements I18nEnumDict<String> {
    unpaid("未付款"),
    paid("付款成功"),
    successAccept("确认收货")
    ;

    private final String text;
    @Override
    public String getValue() {
        return name();
    }



}

package org.jetlinks.community.example.enums;


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
    //订单类型包含3种
    normOrder("标准采购订单"),
    planOrder("计划采购订单"),
    contractOrder("合同采购订单")
    ;

    private final String text;
    @Override
    public String getValue() {
        return name();
    }



}

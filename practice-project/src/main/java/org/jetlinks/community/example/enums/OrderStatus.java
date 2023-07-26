package org.jetlinks.community.example.enums;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.Dict;
import org.hswebframework.web.dict.EnumDict;
import org.hswebframework.web.dict.I18nEnumDict;
import java.*;


@Getter
@AllArgsConstructor
@Dict("order-type")
public enum OrderStatus implements I18nEnumDict<String> {
    //订单状态：包含3种
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

package org.jetlinks.community.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;
import org.hswebframework.ezorm.rdb.mapping.annotation.DefaultValue;
import org.hswebframework.ezorm.rdb.mapping.annotation.EnumCodec;
import org.hswebframework.web.api.crud.entity.GenericEntity;
import org.hswebframework.web.api.crud.entity.RecordCreationEntity;
import org.hswebframework.web.api.crud.entity.RecordModifierEntity;
import org.hswebframework.web.crud.annotation.EnableEntityEvent;
import org.hswebframework.web.crud.generator.Generators;
import org.hswebframework.web.utils.DigestUtils;
import org.hswebframework.web.validator.CreateGroup;
import org.jetlinks.community.example.enums.OrderStatus;
import org.jetlinks.community.example.enums.OrderType;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.sql.JDBCType;

/**
 * 订单管理实体类
 *
 * @author wangyujie
 * @since 1.0
 */
@Table(name = "s_order")
@Getter
@Setter
@Schema(description = "订单管理表")
@EnableEntityEvent //开启实体类crud事件
public class OrderEntity extends GenericEntity<String>
//实现这2个接口标记此实体类需要记录创建人修改人信息，在crud时会自动填充对应的信息。
    implements RecordCreationEntity, RecordModifierEntity {

    //订单流水号自动生成，不可修改
    @Column(updatable = false)
    @Schema(description = "订单流水号")
    @GeneratedValue
    private  String orderSerialNumber;



    //平台ID长度统一64,商品id,不可修改
    @Column(length = 64, nullable = false, updatable = false)
    @NotBlank(groups = CreateGroup.class)
    @Schema(description = "商品ID", accessMode = Schema.AccessMode.READ_ONLY)
    private String productId;


    @Column
    @EnumCodec
    @ColumnType(javaType = String.class, jdbcType = JDBCType.BIGINT)
    @Schema(description = "订单类型")
    private OrderType orderType;

    @Column
    @EnumCodec
    @ColumnType(javaType = String.class, jdbcType = JDBCType.BIGINT)
    @Schema(description = "订单状态")
    private OrderStatus orderStatus;

    //平台ID长度统一64,创建人不为空,不可修改
    @Column(length = 64, nullable = false, updatable = false)
    @Schema(description = "创建人ID", accessMode = Schema.AccessMode.READ_ONLY)
    private String creatorId;

    @Column(length = 64, nullable = false)
    @Schema(description = "修改人ID", accessMode = Schema.AccessMode.READ_ONLY)
    private String modifierId;

    @Column(nullable = false)
    @DefaultValue(generator = Generators.CURRENT_TIME)
    @Schema(description = "修改时间", accessMode = Schema.AccessMode.READ_ONLY)
    private Long modifyTime;

    @Column(nullable = false, updatable = false)
    @DefaultValue(generator = Generators.CURRENT_TIME)
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private Long createTime;

    @Override
    public String getId() {
        if (super.getId() == null) {
            generateId();
        }
        return super.getId();
    }

    public void generateId() {
        String id = generateOrderId(orderSerialNumber, productId);
        setId(id);
    }

    public static String generateOrderId(String orderSerialNumber, String productId) {
        return DigestUtils.md5Hex(String.join(orderSerialNumber, "|", productId));
    }

}
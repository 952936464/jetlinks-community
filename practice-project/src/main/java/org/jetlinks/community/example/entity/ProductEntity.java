package org.jetlinks.community.example.entity;

import org.checkerframework.checker.units.qual.C;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;
import org.hswebframework.ezorm.rdb.mapping.annotation.Comment;
import org.hswebframework.ezorm.rdb.mapping.annotation.EnumCodec;
import org.hswebframework.web.api.crud.entity.GenericEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.annotation.DefaultValue;
import org.hswebframework.web.api.crud.entity.RecordCreationEntity;
import org.hswebframework.web.api.crud.entity.RecordModifierEntity;
import org.hswebframework.web.crud.annotation.EnableEntityEvent;
import org.hswebframework.web.crud.generator.Generators;
import org.hswebframework.web.validator.CreateGroup;
import org.jetlinks.community.example.enums.ProductType;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.JDBCType;

/**
 * 商品管理实体类
 *
 * @author wangyujie
 * @since 1.0
 */

@Table(name = "s_product")
@Getter
@Setter
@Comment("商品实体类")
@EnableEntityEvent //开启实体类crud事件
public class ProductEntity extends GenericEntity<String>
//实现这2个接口标记此实体类需要记录创建人修改人信息，在crud时会自动填充对应的信息。
    implements RecordCreationEntity, RecordModifierEntity {

    //平台ID长度统一64,商品id,不可修改




    @Column(length = 64, nullable = false)
    @NotBlank(groups = CreateGroup.class)
    @Schema(description = "名称")
    private String name;

    @Column
    @Pattern(regexp = "[a-zA-Z]{3}@[0-9]{2}", message = "3位字母编码@2为数字编码，例如abc@12")
    @Schema(description = "商品批次")
    private String productBatch;



    @Column
    @EnumCodec
    @ColumnType(javaType = String.class)
    @Schema(description = "商品分类")
    private ProductType productType;


    @GeneratedValue(generator = Generators.CURRENT_TIME)
    @Column
    @Schema(description = "上架日期", accessMode = Schema.AccessMode.READ_ONLY)
    private Long upTime;

    @Column
    @Schema(description = "下架日期", accessMode = Schema.AccessMode.READ_ONLY)
    private Long downTime;

    //平台ID长度统一64,创建人不为空,不可修改
    @Column(length = 64, nullable = false, updatable = false)
    @Schema(description = "创建人ID", accessMode = Schema.AccessMode.READ_ONLY)
    private String creatorId;

    @Column(length = 64, nullable = false)
    @Schema(description = "修改人ID", accessMode = Schema.AccessMode.READ_ONLY)
    private String modifierId;

    @Column(nullable = false, updatable = false)
    @DefaultValue(generator = Generators.CURRENT_TIME)
    @Schema(description = "修改时间", accessMode = Schema.AccessMode.READ_ONLY)
    private Long modifyTime;

    @Column(nullable = false, updatable = false)
    @DefaultValue(generator = Generators.CURRENT_TIME)
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private Long createTime;

}

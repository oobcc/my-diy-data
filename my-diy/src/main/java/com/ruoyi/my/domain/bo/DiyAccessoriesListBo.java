package com.ruoyi.my.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 配件单业务对象 diy_accessories_list
 *
 * @author ruoyi
 * @date 2022-12-05
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DiyAccessoriesListBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 配件单名
     */
    @NotBlank(message = "配件单名不能为空", groups = {AddGroup.class, EditGroup.class})
    private String name;

    /**
     * 配件单类别
     */
    private String type;

    /**
     * cpu
     */
    private Long cpuId;

    /**
     * 主板
     */
    private Long motherboardId;

    /**
     * 显卡
     */
    private Long graphicsCardId;

    /**
     * 内存
     */
    private Long memoryId;

    /**
     * 储存
     */
    private Long storeId;

    /**
     * 电源
     */
    private Long powerId;

    /**
     * 机箱
     */
    private Long chassisId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 标签
     */
    private List<Long> label;

    /**
     * 备注
     */
    private String remark;


}

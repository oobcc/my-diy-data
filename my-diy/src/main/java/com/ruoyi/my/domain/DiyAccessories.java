package com.ruoyi.my.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 配件信息对象 diy_accessories
 *
 * @author ruoyi
 * @date 2022-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("diy_accessories")
public class DiyAccessories extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配件id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 配件名
     */
    private String name;
    /**
     * 配件编号
     */
    private String number;
    /**
     * 配件价格
     */
    private BigDecimal price;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}

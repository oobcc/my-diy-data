package com.ruoyi.my.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 配件单对象 diy_accessories_list
 *
 * @author ruoyi
 * @date 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("diy_accessories_list")
public class DiyAccessoriesList extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 配件单名
     */
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
    private String label;

    /**
     * 备注
     */
    private String remark;

}

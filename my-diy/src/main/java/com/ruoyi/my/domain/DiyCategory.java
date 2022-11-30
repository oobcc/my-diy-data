package com.ruoyi.my.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 配件类别对象 diy_category
 *
 * @author ruoyi
 * @date 2022-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("diy_category")
public class DiyCategory extends TreeEntity<DiyCategory> {

    private static final long serialVersionUID=1L;

    /**
     * 配件类别id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 配件类别名
     */
    private String name;
    /**
     * 不兼容类别id
     */
    private String incompatible;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}

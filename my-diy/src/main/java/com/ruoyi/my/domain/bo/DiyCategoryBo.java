package com.ruoyi.my.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 配件类别业务对象 diy_category
 *
 * @author ruoyi
 * @date 2022-11-29
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DiyCategoryBo extends TreeEntity<DiyCategoryBo> {

    /**
     * 配件类别id
     */
    @NotNull(message = "配件类别id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 配件类别名
     */
    @NotBlank(message = "配件类别名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 不兼容类别id
     */
    private String incompatible;


}

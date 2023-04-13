package com.ruoyi.my.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 标签管理业务对象 diy_lable
 *
 * @author ruoyi
 * @date 2022-12-16
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DiyLableBo extends BaseEntity {

    /**
     * id
     */
    private Integer id;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不能为空", groups = {AddGroup.class, EditGroup.class})
    private String name;

    /**
     * 配件单类别，0表示系统标签，1表示用户添加的标签
     */
    private String type;


}

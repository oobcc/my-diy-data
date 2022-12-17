package com.ruoyi.my.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.my.handler.LongArrayListTypeHandler;
import java.util.List;
import lombok.Data;


/**
 * 标签管理对象 diy_lable
 *
 * @author ruoyi
 * @date 2022-12-16
 */
@Data
@TableName(value = "diy_lable", autoResultMap = true)
public class DiyLable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 标签名
     */
    private String name;
    /**
     * 配件单类别，0表示系统标签，1表示用户添加的标签
     */
    private String type;

    /**
     * 添加到这个标签的配件单
     */

    @TableField(typeHandler = LongArrayListTypeHandler.class)
    private List<Long> accessListIds;

}

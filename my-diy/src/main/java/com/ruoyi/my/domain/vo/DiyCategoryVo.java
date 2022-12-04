package com.ruoyi.my.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import java.util.List;
import lombok.Data;
import java.util.Date;


/**
 * 配件类别视图对象 diy_category
 *
 * @author ruoyi
 * @date 2022-11-29
 */
@Data
@ExcelIgnoreUnannotated
public class DiyCategoryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 配件类别id
     */
    @ExcelProperty(value = "配件类别id")
    private Long id;

    /**
     * 父id
     */
    @ExcelProperty(value = "父id")
    private Long parentId;

    /**
     * 配件类别名
     */
    @ExcelProperty(value = "配件类别名")
    private String name;

    /**
     * 不兼容类别id
     */
    @ExcelProperty(value = "不兼容类别id")
    private List<String> incompatible;


    /**
     * 祖级列表
     */
    private String ancestors;
}

package com.ruoyi.my.domain.vo;

import com.ruoyi.my.domain.DiyAccessories;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import java.util.List;
import lombok.Data;
import java.util.Date;


/**
 * 配件单视图对象 diy_accessories_list
 *
 * @author ruoyi
 * @date 2022-12-05
 */
@Data
@ExcelIgnoreUnannotated
public class DiyAccessoriesListVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 配件单名
     */
    @ExcelProperty(value = "配件单名")
    private String name;

    /**
     * 配件单类别
     */
    @ExcelProperty(value = "配件单类别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "diy_accessories_category_type")
    private String type;

    /**
     * cpu
     */
    @ExcelProperty(value = "cpu")
    private Long cpuId;

    /**
     * 主板
     */
    @ExcelProperty(value = "主板")
    private Long motherboardId;

    /**
     * 显卡
     */
    @ExcelProperty(value = "显卡")
    private Long graphicsCardId;

    /**
     * 内存
     */
    @ExcelProperty(value = "内存")
    private Long memoryId;

    /**
     * 储存
     */
    @ExcelProperty(value = "储存")
    private Long storeId;

    /**
     * 电源
     */
    @ExcelProperty(value = "电源")
    private Long powerId;

    /**
     * 机箱
     */
    @ExcelProperty(value = "机箱")
    private Long chassisId;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 标签
     */
    @ExcelProperty(value = "标签")
    private List<Long> label;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}

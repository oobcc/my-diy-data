package com.ruoyi.my.domain.vo;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Data;


/**
 * 配件信息视图对象 diy_accessories
 *
 * @author ruoyi
 * @date 2022-11-29
 */
@Data
@ExcelIgnoreUnannotated
public class DiyAccessoriesVo {

    private static final long serialVersionUID = 1L;

    /**
     * 配件id
     */
    @ExcelProperty(value = "配件id")
    private Long id;

    /**
     * 配件名
     */
    @ExcelProperty(value = "配件名")
    private String name;

    /**
     * 配件编号
     */
    @ExcelProperty(value = " 配件编号")
    private String number;

    /**
     * 配件价格
     */
    @ExcelProperty(value = "配件价格")
    private BigDecimal price;

    /**
     * 警告
     */
    @ExcelProperty(value = "警告信息")
    private String warning;

    /**
     * 配件类别
     */
    @ExcelProperty(value = "配件类别")
    private Long categoryId;

}

package com.ruoyi.my.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;


/**
 * 标签管理视图对象 diy_lable
 *
 * @author ruoyi
 * @date 2022-12-16
 */
@Data
@ExcelIgnoreUnannotated
public class DiyLableVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 标签名
     */
    @ExcelProperty(value = "标签名")
    private String name;

    /**
     * 配件单类别，0表示系统标签，1表示用户添加的标签
     */
    @ExcelProperty(value = "配件单类别，0表示系统标签，1表示用户添加的标签", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "diy_accessories_category_type")
    private String type;


}

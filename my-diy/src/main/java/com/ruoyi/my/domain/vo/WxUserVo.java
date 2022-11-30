package com.ruoyi.my.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;


/**
 * wx 端用户信息视图对象 wx_user
 *
 * @author ruoyi
 * @date 2022-11-22
 */
@Data
@ExcelIgnoreUnannotated
public class WxUserVo {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户openid
     */
    @ExcelProperty(value = "用户openid")
    private String openId;

    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    private String phonenumber;

    /**
     * 用户头像
     */
    @ExcelProperty(value = "用户头像")
    private String avatar;

    /**
     * 是否更新过用户信息
     */
    @ExcelProperty(value = "是否更新过用户信息", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "wx_user_init")
    private String isInit;


}

package com.ruoyi.my.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * wx 端用户信息对象 wx_user
 *
 * @author ruoyi
 * @date 2022-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("wx_user")
public class WxUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id")
    private Long userId;
    /**
     * 用户openid
     */
    private Long openId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 是否更新过用户信息
     */
    private String isInit;

}

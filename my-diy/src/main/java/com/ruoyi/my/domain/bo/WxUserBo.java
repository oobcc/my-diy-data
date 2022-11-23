package com.ruoyi.my.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * wx 端用户信息业务对象 wx_user
 *
 * @author ruoyi
 * @date 2022-11-22
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class WxUserBo extends BaseEntity {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 用户openid
     */
    @NotNull(message = "用户openid不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long openId;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickName;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phonenumber;

    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String avatar;

    /**
     * 是否更新过用户信息
     */
    @NotBlank(message = "是否更新过用户信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isInit;


}

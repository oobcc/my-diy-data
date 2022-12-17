package com.ruoyi.my.domain.vo;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.ruoyi.my.domain.WxUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {

    private SaTokenInfo saTokenInfo;
    private WxUser user;
}

package com.ruoyi.my.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.domain.dto.LoginDto;
import com.ruoyi.my.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对接 wx api
 */

@RestController
@RequestMapping("/my/public")
public class wxApiController {


    @Autowired
    private WxService wxService;

    /**
     * 小程序获取用户信息
     *
     * @param code
     * @return Object
     */
    @SaIgnore
    @PostMapping("login")
    public R<SaTokenInfo> login(@RequestBody LoginDto code) {
        return wxService.login(code.getCode());
    }

    @GetMapping("checkLogin")
    public R<Void> checkLogin() {
        return R.ok("登录中");
    }
}

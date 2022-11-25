package com.ruoyi.my.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.annotation.SaIgnore;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.service.WxService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R<WxMaJscode2SessionResult> login(@RequestBody String code) {

        return wxService.login(code);
    }

    @Data
    static class Test {
        private String code;
    }
}

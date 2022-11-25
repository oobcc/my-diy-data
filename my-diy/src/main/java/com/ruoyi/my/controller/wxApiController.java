package com.ruoyi.my.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.domain.vo.LoginVo;
import com.ruoyi.my.service.WxService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
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
    public R<LoginVo> login(@RequestBody String code) {
        return wxService.login(code);
    }

    @Data
    static class Test {

        private String code;
    }
}

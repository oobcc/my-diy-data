package com.ruoyi.my.service;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.domain.vo.LoginVo;

public interface WxService {

    R<LoginVo> login(String code);
}

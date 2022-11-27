package com.ruoyi.my.service;


import cn.dev33.satoken.stp.SaTokenInfo;
import com.ruoyi.common.core.domain.R;

public interface WxService {

    R<SaTokenInfo> login(String code);
}

package com.ruoyi.my.service;


import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ruoyi.common.core.domain.R;

public interface WxService {
    R<WxMaJscode2SessionResult> login(String code);
}

package com.ruoyi.my.service;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.domain.vo.LoginVo;
import com.ruoyi.my.domain.vo.RecommendVo;

public interface WxService {

    R<LoginVo> login(String code);

    String[] getLabel(String text);

    RecommendVo recommended(String text);
}

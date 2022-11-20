package com.ruoyi.my.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.service.WxService;
import com.xxl.job.admin.controller.JobInfoController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WxServiceImpl implements WxService {

    private static Logger logger = LoggerFactory.getLogger(JobInfoController.class);

    @Autowired
    private WxMaService wxMaService;


    public R<WxMaJscode2SessionResult> login(String code) {
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            logger.debug(session.getSessionKey());
            logger.debug(session.getOpenid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return R.ok(session);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return R.fail(e.toString());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }
}

package com.ruoyi.my.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.domain.WxUser;
import com.ruoyi.my.mapper.WxUserMapper;
import com.ruoyi.my.service.IWxUserService;
import com.ruoyi.my.service.WxService;
import com.xxl.job.admin.controller.JobInfoController;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WxServiceImpl implements WxService {

    private static Logger logger = LoggerFactory.getLogger(JobInfoController.class);

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private WxUserMapper wxUserMapper;

    public R<WxMaJscode2SessionResult> login(String code) {
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            logger.debug(session.getSessionKey());
            logger.debug(session.getOpenid());
            WxUser wxUser = wxUserMapper.selectOne(new QueryWrapper<WxUser>()
                .lambda()
                .eq(WxUser::getOpenId, session.getOpenid()));
            if (wxUser == null) {
                String prefix = "用户";
                Integer suffix = 5000 + (int) (Math.random() * 1000);
                List<WxUser> wxUsers = wxUserMapper.selectList(new QueryWrapper<WxUser>()
                    .lambda().eq(WxUser::getNickName,
                        String.format("%s%s", prefix, suffix)));
                if (wxUsers.size() > 0) {

                }


                wxUser = WxUser
                    .builder()
                    .openId(Long.parseLong(session.getOpenid()))
                    .build();


            }

            return R.ok(session);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return R.fail(e.toString());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }
}

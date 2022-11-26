package com.ruoyi.my.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.domain.WxUser;
import com.ruoyi.my.domain.vo.LoginVo;
import com.ruoyi.my.mapper.WxUserMapper;
import com.ruoyi.my.service.WxService;
import com.xxl.job.admin.controller.JobInfoController;

import java.util.List;

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

    @Autowired
    private WxUserMapper wxUserMapper;

    public R<LoginVo> login(String code) {
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            logger.debug(session.getSessionKey());
            logger.debug(session.getOpenid());
            WxUser wxUser = wxUserMapper.selectOne(new QueryWrapper<WxUser>()
                .lambda()
                .eq(WxUser::getOpenId, session.getOpenid()));
            if (wxUser == null) {
                String prefix = "用户";
                // TODO 随机不合理 需要优化
                Integer suffix = 5000 + (int) (Math.random() * 10000);
                List<WxUser> wxUsers = wxUserMapper.selectList(new QueryWrapper<WxUser>()
                    .lambda().eq(WxUser::getNickName,
                        String.format("%s%s", prefix, suffix)));
                // 判断用户名是非唯一，唯一就重新随机一条
                while (wxUsers.size() != 0) {
                    suffix = 5000 + (int) (Math.random() * 1000);
                    wxUsers = wxUserMapper.selectList(new QueryWrapper<WxUser>()
                        .lambda().eq(WxUser::getNickName,
                            String.format("%s%s", prefix, suffix)));
                }
                // 设置openid和用户名
                wxUser = WxUser.builder().openId(session.getOpenid())
                    .nickName(String.format("%s%s", prefix, suffix))
                    .userType("app_user")
                    .build();
                wxUserMapper.insert(wxUser);

                log.info("userId:{} 插入成功", wxUser.getUserId());
            }
            StpUtil.login(wxUser.getUserId());
            return R.ok(new LoginVo(StpUtil.getTokenName(), StpUtil.getTokenValue()));
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return R.fail(e.toString());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }
}

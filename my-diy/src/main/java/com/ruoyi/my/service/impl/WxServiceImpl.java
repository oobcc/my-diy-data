package com.ruoyi.my.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.model.XcxLoginUser;
import com.ruoyi.common.enums.DeviceType;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.helper.DataBaseHelper;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.my.config.UrlConfig;
import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.domain.DiyLable;
import com.ruoyi.my.domain.DiyUserAccessoriesList;
import com.ruoyi.my.domain.WxUser;
import com.ruoyi.my.domain.vo.LoginVo;
import com.ruoyi.my.domain.vo.RecommendVo;
import com.ruoyi.my.mapper.DiyAccessoriesListMapper;
import com.ruoyi.my.mapper.DiyLableMapper;
import com.ruoyi.my.mapper.WxUserMapper;
import com.ruoyi.my.service.WxService;
import com.ruoyi.my.service.impl.DiyAccessoriesListServiceImpl.priceResult;
import com.xxl.job.admin.controller.JobInfoController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WxServiceImpl implements WxService {

    private static Logger logger = LoggerFactory.getLogger(JobInfoController.class);

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private WxUserMapper wxUserMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiyLableMapper diyLableMapper;

    @Autowired
    private DiyAccessoriesListMapper diyAccessoriesListMapper;

    @Autowired
    private UrlConfig urlConfig;

    public R<LoginVo> login(String code) {
        try {
            Long userId = LoginHelper.getUserId();
            WxUser wxLoginUser = wxUserMapper.selectById(userId);
            if (ObjectUtil.isNotNull(wxLoginUser)) {
                return getLoginVoR(wxLoginUser);
            }
        } catch (Exception ignored) {
        }

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
                    .build();
                wxUserMapper.insert(wxUser);

                log.info("userId:{} 插入成功", wxUser.getUserId());
            }
            return getLoginVoR(wxUser);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return R.fail(e.toString());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    @Override
    public String[] getLabel(String text) {
        String url = urlConfig.getPredicturl() + "/predict?text=" + text;

        ObjectMapper mapper = new ObjectMapper();
        String content = restTemplate.getForEntity(url, String.class).getBody();
        System.out.println(content);
        R<String> lables = null;
        try {
            lables = mapper.readValue(
                content,
                mapper.getTypeFactory().constructParametricType(R.class, String.class));
            return lables.getData().split(",");
        } catch (JsonProcessingException e) {
            return new String[0];
        }

    }

    @Override
    public RecommendVo recommended(String text) {
        String[] labels = getLabel(text);
        if (labels == null || labels.length == 0) {
            return new RecommendVo(labels, null);
        }
        List<Long> lablesId = new ArrayList<>();
        for (String label : labels) {
            lablesId.addAll(
                diyLableMapper.selectList(new LambdaQueryWrapper<DiyLable>()
                        .select(DiyLable::getId)
                        .eq(DiyLable::getType, "0")
                        .eq(DiyLable::getName, label)).stream().map(DiyLable::getId)
                    .collect(Collectors.toList()));
        }
        Map<DiyAccessoriesList, Integer> map = new HashMap<>();
        lablesId.forEach(l -> {
            List<DiyAccessoriesList> ancestors = diyAccessoriesListMapper.selectList(
                new LambdaQueryWrapper<DiyAccessoriesList>()
                    .apply(DataBaseHelper.findInSet(l, "label")));
            ancestors.forEach(a -> map.put(a, map.getOrDefault(a, 0) + 1));
        });
        List<Map.Entry<DiyAccessoriesList, Integer>> list = new ArrayList<>(map.entrySet());
        if (list.size() == 0) {
            return new RecommendVo(labels, null);
        }
        list.sort(Comparator.comparingInt(Entry::getValue));
        return new RecommendVo(labels, list.get(list.size() - 1).getKey());
    }

    @NotNull
    private R<LoginVo> getLoginVoR(WxUser wxLoginUser) {
        XcxLoginUser xcxLoginUser1 = new XcxLoginUser();
        xcxLoginUser1.setUserId(wxLoginUser.getUserId());
        xcxLoginUser1.setOpenid(wxLoginUser.getOpenId());
        xcxLoginUser1.setUsername(wxLoginUser.getNickName());
        xcxLoginUser1.setUserType(UserType.APP_USER.getUserType());
        LoginHelper.loginByDevice(xcxLoginUser1, DeviceType.XCX);
        return R.ok(new LoginVo(StpUtil.getTokenInfo(), wxLoginUser));
    }
}

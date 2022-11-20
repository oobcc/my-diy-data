package com.ruoyi.my.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class WxMaConfiguration {

    @Autowired
    private WxConfig wxConfig;

    @Bean
    public WxMaService wxMaService() {
        List<WxConfig.Config> configs = this.wxConfig.getConfigs();
        if (configs == null) {
            log.info("配置为空");
        }
        WxMaService maService = new WxMaServiceImpl();
        maService.setMultiConfigs(
            configs.stream()
                .map(a -> {
                    WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
//                WxMaDefaultConfigImpl config = new WxMaRedisConfigImpl(new JedisPool());
                    // 使用上面的配置时，需要同时引入jedis-lock的依赖，否则会报类无法找到的异常
                    config.setAppid(a.getAppid());
                    config.setSecret(a.getSecret());
                    return config;
                }).collect(Collectors.toMap(WxMaDefaultConfigImpl::getAppid, a -> a, (o, n) -> o)));
        return maService;
    }
}

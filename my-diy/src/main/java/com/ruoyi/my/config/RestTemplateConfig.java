package com.ruoyi.my.config;

import java.nio.charset.Charset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {     //在SpringBoot启动类中注册RestTemplate
        RestTemplate r = new RestTemplate();
        r.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF8")));
        return r;
    }
}

package com.ruoyi.my.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.helper.DataBaseHelper;
import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.domain.DiyLable;
import com.ruoyi.my.domain.vo.RecommendVo;
import com.ruoyi.my.service.IWxUserService;
import com.ruoyi.my.service.WxService;
import com.ruoyi.my.service.impl.DiyAccessoriesListServiceImpl.priceResult;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

class WxServiceImplTest {

    RestTemplate restTemplate = new RestTemplate();

    public void getLabel(String text) {
        String url = "http://127.0.0.1:35600/predict?text=" + text;

        ObjectMapper mapper = new ObjectMapper();
        String content = restTemplate.getForEntity(url, String.class).getBody();
        System.out.println(content);
        R<String> lables = null;
        try {
            lables = mapper.readValue(
                content,
                mapper.getTypeFactory().constructParametricType(R.class, String.class));
            System.out.println(lables.getData());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void recommended() {
        getLabel("办公");
    }
}

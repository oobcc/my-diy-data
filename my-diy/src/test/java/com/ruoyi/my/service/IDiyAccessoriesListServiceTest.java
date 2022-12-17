package com.ruoyi.my.service;

import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.mapper.DiyAccessoriesListMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration
@RequiredArgsConstructor
class IDiyAccessoriesListServiceTest {

    private final DiyAccessoriesListMapper diyAccessoriesListMapper;


    @Test
    void getPriceForList() {
        List<DiyAccessoriesList> diyAccessoriesLists = diyAccessoriesListMapper.selectList();
    }
}

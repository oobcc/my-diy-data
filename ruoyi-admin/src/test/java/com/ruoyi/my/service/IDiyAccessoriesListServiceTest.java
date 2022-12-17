package com.ruoyi.my.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.mapper.DiyAccessoriesListMapper;
import com.ruoyi.my.service.impl.DiyAccessoriesListServiceImpl.priceResult;
import java.util.List;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class IDiyAccessoriesListServiceTest {

    @Autowired
    private DiyAccessoriesListMapper diyAccessoriesListMapper;

    @Autowired
    private IDiyAccessoriesListService iDiyAccessoriesListService;


    @Test
    void getPriceForList() {
        List<DiyAccessoriesList> diyAccessoriesLists = diyAccessoriesListMapper.selectList();
        DiyAccessoriesList diyAccessoriesList = diyAccessoriesLists.get(0);
        R<priceResult> priceForList = iDiyAccessoriesListService.getPriceForList(
            diyAccessoriesList);
        System.out.println(priceForList);

    }
}

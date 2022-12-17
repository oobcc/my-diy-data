package com.ruoyi.my.domain.vo;

import com.ruoyi.my.domain.DiyAccessoriesList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendVo {

    /**
     * 标签
     */
    String[] label;

    /**
     * 配置
     */
    DiyAccessoriesList diyAccessoriesList;
}

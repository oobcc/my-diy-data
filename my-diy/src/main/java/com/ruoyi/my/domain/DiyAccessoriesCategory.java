package com.ruoyi.my.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("diy_accessories_category")
public class DiyAccessoriesCategory {


    @TableId(value = "id")
    private Long id;
    private Long AccessoriesId;
    private Long CategoryId;

}

package com.ruoyi.my.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.domain.vo.DiyAccessoriesListVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 配件单Mapper接口
 *
 * @author ruoyi
 * @date 2022-12-05
 */
public interface DiyAccessoriesListMapper extends
    BaseMapperPlus<DiyAccessoriesListMapper, DiyAccessoriesList, DiyAccessoriesListVo> {

    List<DiyAccessoriesList> getDiyUserAccessoriesListByUserId(@Param("id") Long id);

}

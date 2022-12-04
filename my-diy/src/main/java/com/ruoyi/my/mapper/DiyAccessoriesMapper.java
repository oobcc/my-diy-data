package com.ruoyi.my.mapper;

import com.ruoyi.my.domain.DiyAccessories;
import com.ruoyi.my.domain.vo.DiyAccessoriesVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import java.util.List;
import java.util.Map;
import org.springframework.data.repository.query.Param;

/**
 * 配件信息Mapper接口
 *
 * @author ruoyi
 * @date 2022-11-29
 */
public interface DiyAccessoriesMapper extends
    BaseMapperPlus<DiyAccessoriesMapper, DiyAccessories, DiyAccessoriesVo> {

    List<DiyAccessoriesVo> selectVoList();

}

package com.ruoyi.my.service;

import com.ruoyi.my.domain.DiyAccessories;
import com.ruoyi.my.domain.vo.DiyAccessoriesVo;
import com.ruoyi.my.domain.bo.DiyAccessoriesBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 配件信息Service接口
 *
 * @author ruoyi
 * @date 2022-11-29
 */
public interface IDiyAccessoriesService {

    /**
     * 查询配件信息
     */
    DiyAccessoriesVo queryById(Long id);

    /**
     * 查询配件信息列表
     */
    TableDataInfo<DiyAccessoriesVo> queryPageList(DiyAccessoriesBo bo, PageQuery pageQuery);

    /**
     * 查询配件信息列表
     */
    List<DiyAccessoriesVo> queryList(DiyAccessoriesBo bo);

    /**
     * 新增配件信息
     */
    Boolean insertByBo(DiyAccessoriesBo bo);

    /**
     * 修改配件信息
     */
    Boolean updateByBo(DiyAccessoriesBo bo);

    /**
     * 校验并批量删除配件信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}

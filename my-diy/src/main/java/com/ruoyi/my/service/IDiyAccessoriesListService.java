package com.ruoyi.my.service;

import com.ruoyi.my.domain.DiyAccessories;
import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.domain.DiyCategory;
import com.ruoyi.my.domain.vo.DiyAccessoriesListVo;
import com.ruoyi.my.domain.bo.DiyAccessoriesListBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 配件单Service接口
 *
 * @author ruoyi
 * @date 2022-12-05
 */
public interface IDiyAccessoriesListService {

    /**
     * 查询配件单
     */
    DiyAccessoriesListVo queryById(Long id);

    /**
     * 查询配件单列表
     */
    TableDataInfo<DiyAccessoriesListVo> queryPageList(DiyAccessoriesListBo bo, PageQuery pageQuery);

    /**
     * 查询配件单列表
     */
    List<DiyAccessoriesListVo> queryList(DiyAccessoriesListBo bo);

    /**
     * 新增配件单
     */
    Boolean insertByBo(DiyAccessoriesListBo bo);

    /**
     * 修改配件单
     */
    Boolean updateByBo(DiyAccessoriesListBo bo);

    /**
     * 校验并批量删除配件单信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<DiyAccessories> getCateAcce(Long cateId, DiyAccessoriesList diyAccessoriesList);

    Map<Long, String> getMap();
}

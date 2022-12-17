package com.ruoyi.my.service;

import com.ruoyi.my.domain.DiyLable;
import com.ruoyi.my.domain.vo.DiyLableVo;
import com.ruoyi.my.domain.bo.DiyLableBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 标签管理Service接口
 *
 * @author ruoyi
 * @date 2022-12-16
 */
public interface IDiyLableService {

    /**
     * 查询标签管理
     */
    DiyLableVo queryById(Long id);

    /**
     * 查询标签管理列表
     */
    TableDataInfo<DiyLableVo> queryPageList(DiyLableBo bo, PageQuery pageQuery);

    /**
     * 查询标签管理列表
     */
    List<DiyLableVo> queryList(DiyLableBo bo);

    /**
     * 新增标签管理
     */
    Boolean insertByBo(DiyLableBo bo);

    /**
     * 修改标签管理
     */
    Boolean updateByBo(DiyLableBo bo);

    /**
     * 校验并批量删除标签管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Map<Long, DiyLable> getMap();
}

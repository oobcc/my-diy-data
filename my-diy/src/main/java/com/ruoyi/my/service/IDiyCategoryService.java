package com.ruoyi.my.service;

import cn.hutool.core.lang.tree.Tree;
import com.ruoyi.my.domain.DiyCategory;
import com.ruoyi.my.domain.vo.DiyCategoryVo;
import com.ruoyi.my.domain.bo.DiyCategoryBo;

import java.util.Collection;
import java.util.List;

/**
 * 配件类别Service接口
 *
 * @author ruoyi
 * @date 2022-11-29
 */
public interface IDiyCategoryService {

    /**
     * 查询配件类别
     */
    DiyCategoryVo queryById(Long id);


    /**
     * 查询配件类别列表
     */
    List<DiyCategoryVo> queryList(DiyCategoryBo bo);

    /**
     * 新增配件类别
     */
    Boolean insertByBo(DiyCategoryBo bo);

    /**
     * 修改配件类别
     */
    Boolean updateByBo(DiyCategoryBo bo);

    /**
     * 校验并批量删除配件类别信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


}

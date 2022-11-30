package com.ruoyi.my.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.ruoyi.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.TreeBuildUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.my.domain.bo.DiyCategoryBo;
import com.ruoyi.my.domain.vo.DiyCategoryVo;
import com.ruoyi.my.domain.DiyCategory;
import com.ruoyi.my.mapper.DiyCategoryMapper;
import com.ruoyi.my.service.IDiyCategoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 配件类别Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-29
 */
@RequiredArgsConstructor
@Service
public class DiyCategoryServiceImpl implements IDiyCategoryService {

    private final DiyCategoryMapper baseMapper;

    /**
     * 查询配件类别
     */
    @Override
    public DiyCategoryVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }


    /**
     * 查询配件类别列表
     */
    @Override
    public List<DiyCategoryVo> queryList(DiyCategoryBo bo) {
        LambdaQueryWrapper<DiyCategory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<DiyCategory> buildQueryWrapper(DiyCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<DiyCategory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, DiyCategory::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), DiyCategory::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getIncompatible()), DiyCategory::getIncompatible,
            bo.getIncompatible());
        return lqw;
    }

    /**
     * 新增配件类别
     */
    @Override
    public Boolean insertByBo(DiyCategoryBo bo) {
        DiyCategory add = BeanUtil.toBean(bo, DiyCategory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改配件类别
     */
    @Override
    public Boolean updateByBo(DiyCategoryBo bo) {
        DiyCategory update = BeanUtil.toBean(bo, DiyCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(DiyCategory entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除配件类别
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}

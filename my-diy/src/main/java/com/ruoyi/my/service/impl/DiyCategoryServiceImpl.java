package com.ruoyi.my.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.helper.DataBaseHelper;
import com.ruoyi.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.my.domain.DiyAccessoriesCategory;
import com.ruoyi.my.mapper.DiyAccessoriesCategoryMapper;
import java.util.ArrayList;
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
    private final DiyAccessoriesCategoryMapper diyAccessoriesCategoryMapper;

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
        lqw.eq(ObjectUtil.isNotNull(bo.getIncompatible()), DiyCategory::getIncompatible,
            bo.getIncompatible());
        return lqw;
    }

    /**
     * 新增配件类别
     */
    @Override
    public Boolean insertByBo(DiyCategoryBo bo) {
        DiyCategory add = BeanUtil.toBean(bo, DiyCategory.class);
        DiyCategory parentCategory = baseMapper.selectById(bo.getParentId());
        add.setAncestors(parentCategory.getAncestors() + "," + bo.getParentId());
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
        DiyCategory add = BeanUtil.toBean(bo, DiyCategory.class);
        DiyCategory newParent = baseMapper.selectById(bo.getParentId());
        DiyCategory oldCategory = baseMapper.selectById(bo.getId());
        if (ObjectUtil.isNotNull(newParent) && ObjectUtil.isNotNull(oldCategory)
        ) {
            String newAncestors = newParent.getAncestors() + "," + newParent.getId();
            String oldAncestors = oldCategory.getAncestors();
            add.setAncestors(newAncestors);
            updateCategoryChildren(oldCategory.getId(), newAncestors, oldAncestors);
        }
        return baseMapper.updateById(add) > 0;
    }

    private void updateCategoryChildren(Long id, String newAncestors, String oldAncestors) {
        List<DiyCategory> children = baseMapper.selectList(new LambdaQueryWrapper<DiyCategory>()
            .apply(DataBaseHelper.findInSet(id, "ancestors")));
        List<DiyCategory> list = new ArrayList<>();
        for (DiyCategory c : children) {
            DiyCategory diyCategory = new DiyCategory();
            diyCategory.setId(c.getId());
            diyCategory.setAncestors(c.getAncestors().replaceFirst(oldAncestors, newAncestors));
            list.add(diyCategory);
        }
        if (list.size() > 0) {
            baseMapper.updateBatchById(list);
        }

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

    @Override
    public int deleteCategoryById(Long ids) {
        return baseMapper.deleteById(ids);
    }

    /**
     * 是否存在子节点
     *
     * @param ids 类别ID
     * @return 结果
     */
    @Override
    public boolean checkCategoryIdExistAccessorie(Long ids) {

        return diyAccessoriesCategoryMapper.exists(new LambdaQueryWrapper<DiyAccessoriesCategory>()
            .eq(DiyAccessoriesCategory::getCategoryId, ids));

    }

    /**
     * 是否存在该分类是否存在配件
     *
     * @param ids 类别ID
     * @return 结果
     */
    @Override
    public boolean hasChildByCategoryId(Long ids) {
        return baseMapper.exists(new LambdaQueryWrapper<DiyCategory>()
            .eq(DiyCategory::getParentId, ids));
    }


}

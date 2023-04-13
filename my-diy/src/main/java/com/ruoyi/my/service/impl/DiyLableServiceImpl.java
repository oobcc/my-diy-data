package com.ruoyi.my.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.my.domain.bo.DiyLableBo;
import com.ruoyi.my.domain.vo.DiyLableVo;
import com.ruoyi.my.domain.DiyLable;
import com.ruoyi.my.mapper.DiyLableMapper;
import com.ruoyi.my.service.IDiyLableService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 标签管理Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-16
 */
@RequiredArgsConstructor
@Service
public class DiyLableServiceImpl implements IDiyLableService {

    private final DiyLableMapper baseMapper;

    /**
     * 查询标签管理
     */
    @Override
    public DiyLableVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询标签管理列表
     */
    @Override
    public TableDataInfo<DiyLableVo> queryPageList(DiyLableBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<DiyLable> lqw = buildQueryWrapper(bo);
        Page<DiyLableVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询标签管理列表
     */
    @Override
    public List<DiyLableVo> queryList(DiyLableBo bo) {
        LambdaQueryWrapper<DiyLable> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<DiyLable> buildQueryWrapper(DiyLableBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<DiyLable> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, DiyLable::getId, bo.getId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), DiyLable::getName, bo.getName());
        return lqw;
    }

    /**
     * 新增标签管理
     */
    @Override
    public Boolean insertByBo(DiyLableBo bo) {
        DiyLable add = BeanUtil.toBean(bo, DiyLable.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改标签管理
     */
    @Override
    public Boolean updateByBo(DiyLableBo bo) {
        DiyLable update = BeanUtil.toBean(bo, DiyLable.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(DiyLable entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除标签管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Map<Integer, DiyLable> getMap() {
        List<DiyLable> diyLables = baseMapper.selectList();
        return diyLables.stream()
            .collect(Collectors.toMap(DiyLable::getId, d -> d));
    }
}

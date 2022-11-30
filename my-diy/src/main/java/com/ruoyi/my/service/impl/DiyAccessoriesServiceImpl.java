package com.ruoyi.my.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.my.domain.bo.DiyAccessoriesBo;
import com.ruoyi.my.domain.vo.DiyAccessoriesVo;
import com.ruoyi.my.domain.DiyAccessories;
import com.ruoyi.my.mapper.DiyAccessoriesMapper;
import com.ruoyi.my.service.IDiyAccessoriesService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 配件信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-29
 */
@RequiredArgsConstructor
@Service
public class DiyAccessoriesServiceImpl implements IDiyAccessoriesService {

    private final DiyAccessoriesMapper baseMapper;

    /**
     * 查询配件信息
     */
    @Override
    public DiyAccessoriesVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询配件信息列表
     */
    @Override
    public TableDataInfo<DiyAccessoriesVo> queryPageList(DiyAccessoriesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<DiyAccessories> lqw = buildQueryWrapper(bo);
        Page<DiyAccessoriesVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询配件信息列表
     */
    @Override
    public List<DiyAccessoriesVo> queryList(DiyAccessoriesBo bo) {
        LambdaQueryWrapper<DiyAccessories> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<DiyAccessories> buildQueryWrapper(DiyAccessoriesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<DiyAccessories> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), DiyAccessories::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getUrl()), DiyAccessories::getUrl, bo.getUrl());
        lqw.eq(bo.getPrice() != null, DiyAccessories::getPrice, bo.getPrice());
        return lqw;
    }

    /**
     * 新增配件信息
     */
    @Override
    public Boolean insertByBo(DiyAccessoriesBo bo) {
        DiyAccessories add = BeanUtil.toBean(bo, DiyAccessories.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改配件信息
     */
    @Override
    public Boolean updateByBo(DiyAccessoriesBo bo) {
        DiyAccessories update = BeanUtil.toBean(bo, DiyAccessories.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(DiyAccessories entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除配件信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}

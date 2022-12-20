package com.ruoyi.my.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.helper.DataBaseHelper;
import com.ruoyi.common.utils.StreamUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.my.config.UrlConfig;
import com.ruoyi.my.domain.DiyAccessoriesCategory;
import com.ruoyi.my.domain.DiyCategory;
import com.ruoyi.my.mapper.DiyAccessoriesCategoryMapper;
import com.ruoyi.my.mapper.DiyCategoryMapper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
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
import org.springframework.web.client.RestTemplate;

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
    private final RestTemplate restTemplate;
    private final DiyAccessoriesCategoryMapper diyAccessoriesCategoryMapper;

    private final DiyCategoryMapper diyCategoryMapper;

    private final UrlConfig urlConfig;

    /**
     * 查询配件信息
     */
    @Override
    public DiyAccessoriesVo queryById(Long id) {
        DiyAccessoriesVo diyAccessoriesVo = baseMapper.selectVoById(id);
        diyAccessoriesVo.setCategoryId(
            diyAccessoriesCategoryMapper
                .selectOne(new LambdaQueryWrapper<DiyAccessoriesCategory>()
                    .eq(DiyAccessoriesCategory::getAccessoriesId, id)).getCategoryId()
        );
        return diyAccessoriesVo;
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
        return baseMapper.selectVoList();
    }

    private LambdaQueryWrapper<DiyAccessories> buildQueryWrapper(DiyAccessoriesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<DiyAccessories> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), DiyAccessories::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getNumber()), DiyAccessories::getNumber, bo.getNumber());
        lqw.eq(bo.getPrice() != null, DiyAccessories::getPrice, bo.getPrice());
        List<DiyCategory> categories = diyCategoryMapper.selectList(
            new LambdaQueryWrapper<DiyCategory>()
                .select(DiyCategory::getId)
                .apply(DataBaseHelper.findInSet(bo.getParentId(), "ancestors")));
        List<Long> ids = StreamUtils.toList(categories, DiyCategory::getId);
        ids.add(bo.getParentId());
        List<DiyAccessoriesCategory> diyAccessoriesCategories = diyAccessoriesCategoryMapper.selectList(
            new LambdaQueryWrapper<DiyAccessoriesCategory>()
                .select(DiyAccessoriesCategory::getAccessoriesId)
                .and(c -> c.in(DiyAccessoriesCategory::getCategoryId, ids))
        );
        List<Long> das = StreamUtils.toList(diyAccessoriesCategories,
            DiyAccessoriesCategory::getAccessoriesId);
        lqw.and(das.size() > 0, w -> w.in(DiyAccessories::getId, das));
        lqw.eq(das.size() == 0, DiyAccessories::getId, -1);
        return lqw;
    }

    /**
     * 新增配件信息
     */
    @Override
    public Boolean insertByBo(DiyAccessoriesBo bo) {
        if (ObjectUtil.isNotNull(bo.getNumber())) {
            List<DiyAccessories> diyAccessories = baseMapper.selectList(
                new LambdaQueryWrapper<DiyAccessories>().eq(DiyAccessories::getNumber,
                    bo.getNumber()));
            if (diyAccessories.size() > 0) {
                DiyAccessories diyAccessories1 = diyAccessories.get(0);
                if (Objects.equals(diyAccessories1.getDelFlag(), "0")) {
                    throw new RuntimeException(String.format("已经添加过配件编号为%s的配件", bo.getNumber()));
                } else {
                    diyAccessories1.setDelFlag("0");
                    baseMapper.updateById(diyAccessories1);
                    bo.setId(diyAccessories1.getId());
                    return updateByBo(bo);
                }
            }
        }
        DiyAccessories add = BeanUtil.toBean(bo, DiyAccessories.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        diyAccessoriesCategoryMapper.insert(
            DiyAccessoriesCategory.builder().AccessoriesId(bo.getId())
                .CategoryId(bo.getCategoryId()).build());
        return flag;
    }

    /**
     * 修改配件信息
     */
    @Override
    public Boolean updateByBo(DiyAccessoriesBo bo) {
        DiyAccessoriesCategory diyAccessoriesCategory = diyAccessoriesCategoryMapper.selectOne(
            new QueryWrapper<DiyAccessoriesCategory>().lambda()
                .eq(DiyAccessoriesCategory::getAccessoriesId, bo.getId()));
        if (!bo.getCategoryId().equals(diyAccessoriesCategory.getCategoryId())) {
            diyAccessoriesCategory.setCategoryId(bo.getCategoryId());
            diyAccessoriesCategoryMapper.updateById(diyAccessoriesCategory);
        }
        DiyAccessories update = BeanUtil.toBean(bo, DiyAccessories.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(DiyAccessories entity) {
        //TODO 做一些数据校验,如唯一约束

    }

    /**
     * 批量删除配件信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        AtomicInteger count = new AtomicInteger();
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        ids.forEach(l -> {
            DiyAccessories diyAccessories = baseMapper.selectById(l);
            diyAccessories.setDelFlag("1");
            baseMapper.updateById(diyAccessories);
            diyAccessoriesCategoryMapper.delete(
                new QueryWrapper<DiyAccessoriesCategory>().lambda()
                    .eq(DiyAccessoriesCategory::getAccessoriesId, l));
            count.getAndIncrement();
        });
        return count.get() == ids.size();
    }

    @Override
    public R<String> getPrice(String number) {
        ObjectMapper mapper = new ObjectMapper();
        String url = urlConfig.getPriceurl() + "?id=" + number;
        try {
            return mapper.readValue(
                restTemplate.getForObject(url, String.class),
                mapper.getTypeFactory().constructParametricType(R.class, String.class));
        } catch (JsonProcessingException e) {
            return R.fail("失败");
        }
    }
}

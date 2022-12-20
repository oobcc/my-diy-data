package com.ruoyi.my.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.helper.DataBaseHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.my.config.UrlConfig;
import com.ruoyi.my.domain.DiyAccessories;
import com.ruoyi.my.domain.DiyAccessoriesCategory;
import com.ruoyi.my.domain.DiyCategory;
import com.ruoyi.my.domain.DiyLable;
import com.ruoyi.my.domain.DiyUserAccessoriesList;
import com.ruoyi.my.mapper.DiyAccessoriesCategoryMapper;
import com.ruoyi.my.mapper.DiyAccessoriesMapper;
import com.ruoyi.my.mapper.DiyCategoryMapper;
import com.ruoyi.my.mapper.DiyLableMapper;
import com.ruoyi.my.mapper.DiyUserAccessoriesListMapper;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ruoyi.my.domain.bo.DiyAccessoriesListBo;
import com.ruoyi.my.domain.vo.DiyAccessoriesListVo;
import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.mapper.DiyAccessoriesListMapper;
import com.ruoyi.my.service.IDiyAccessoriesListService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import org.springframework.web.client.RestTemplate;

/**
 * 配件单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-05
 */
@RequiredArgsConstructor
@Service
public class DiyAccessoriesListServiceImpl implements IDiyAccessoriesListService {

    private final DiyAccessoriesListMapper baseMapper;

    private final DiyCategoryMapper diyCategoryMapper;

    private final DiyAccessoriesCategoryMapper diyAccessoriesCategoryMapper;

    private final DiyAccessoriesMapper diyAccessoriesMapper;

    private final DiyUserAccessoriesListMapper diyUserAccessoriesListMapper;

    private final RestTemplate restTemplate;

    private final DiyLableMapper diyLableMapper;

    private final UrlConfig urlConfig;

    /**
     * 查询配件单
     */
    @Override
    public DiyAccessoriesListVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询配件单列表
     */
    @Override
    public TableDataInfo<DiyAccessoriesListVo> queryPageList(DiyAccessoriesListBo bo,
        PageQuery pageQuery) {
        LambdaQueryWrapper<DiyAccessoriesList> lqw = buildQueryWrapper(bo);
        Page<DiyAccessoriesListVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询配件单列表
     */
    @Override
    public List<DiyAccessoriesListVo> queryList(DiyAccessoriesListBo bo) {
        LambdaQueryWrapper<DiyAccessoriesList> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<DiyAccessoriesList> buildQueryWrapper(DiyAccessoriesListBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<DiyAccessoriesList> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), DiyAccessoriesList::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), DiyAccessoriesList::getType, bo.getType());
        lqw.eq(bo.getCpuId() != null, DiyAccessoriesList::getCpuId, bo.getCpuId());
        lqw.eq(bo.getMotherboardId() != null, DiyAccessoriesList::getMotherboardId,
            bo.getMotherboardId());
        lqw.eq(bo.getGraphicsCardId() != null, DiyAccessoriesList::getGraphicsCardId,
            bo.getGraphicsCardId());
        lqw.eq(bo.getMemoryId() != null, DiyAccessoriesList::getMemoryId, bo.getMemoryId());
        lqw.eq(bo.getStoreId() != null, DiyAccessoriesList::getStoreId, bo.getStoreId());
        lqw.eq(bo.getPowerId() != null, DiyAccessoriesList::getPowerId, bo.getPowerId());
        lqw.eq(bo.getChassisId() != null, DiyAccessoriesList::getChassisId, bo.getChassisId());
        lqw.eq(bo.getPrice() != null, DiyAccessoriesList::getPrice, bo.getPrice());
        lqw.eq(StringUtils.isNotBlank(bo.getRemark()), DiyAccessoriesList::getRemark,
            bo.getRemark());
        return lqw;
    }

    /**
     * 新增配件单
     */
    @Override
    public Boolean insertByBo(DiyAccessoriesListBo bo) {
        DiyAccessoriesList add = BeanUtil.toBean(bo, DiyAccessoriesList.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改配件单
     */
    @Override
    public Boolean updateByBo(DiyAccessoriesListBo bo) {
        DiyAccessoriesList update = BeanUtil.toBean(bo, DiyAccessoriesList.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(DiyAccessoriesList entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除配件单
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


    @SneakyThrows
    @Override
    public List<DiyAccessories> getCateAcce(Long cateId, DiyAccessoriesList diyAccessoriesList) {
        Field[] fields = diyAccessoriesList.getClass().getDeclaredFields();
        Map<String, Long> map = new HashMap<>();
        for (Field f : fields) {
            if (f.getType() == Long.class && !f.getName().equals("id")) {
                f.setAccessible(true);
                if (null != f.get(diyAccessoriesList)) {
                    map.put(f.getName(), (Long) f.get(diyAccessoriesList));
                }
            }
        }
        return getExcludeCateAcce(cateId, map);
    }

    @Override
    public Map<Long, DiyAccessories> getMap() {
        List<DiyAccessories> diyAccessories = diyAccessoriesMapper.selectList();
        return diyAccessories.stream()
            .collect(Collectors.toMap(DiyAccessories::getId, d -> d));
    }


    private List<DiyAccessories> getExcludeCateAcce(Long cateId, Map<String, Long> map) {
        Map<String, Long> kAccListAccIdVCateId = new HashMap<>() {{
            put("cpuId", 2L);
            put("motherboardId", 3L);
            put("graphicsCardId", 4L);
            put("memoryId", 5L);
            put("storeId", 6L);
            put("powerId", 7L);
            put("chassisId", 8L);
        }};
        Set<String> incompatibleCategories = new HashSet<>(); //所有的不兼容类别
        Set<String> set2 = new HashSet<>(); //所有的添加过的列别，用于方向判别

        //添加不兼容类型
        map.forEach((k, v) -> {
            if (!Objects.equals(kAccListAccIdVCateId.getOrDefault(k, 0L), cateId)) {
                DiyAccessoriesCategory diyAccessoriesCategory =
                    diyAccessoriesCategoryMapper.selectOne(
                        new LambdaQueryWrapper<DiyAccessoriesCategory>()
                            .select(DiyAccessoriesCategory::getCategoryId)
                            .eq(DiyAccessoriesCategory::getAccessoriesId, v));

                DiyCategory diyCategory = diyCategoryMapper.selectById(
                    diyAccessoriesCategory.getCategoryId());

                String[] ancestors = diyCategory.getAncestors().split(",");
                List<String> ancestorsList = new ArrayList<>(ancestors.length);
                Collections.addAll(ancestorsList, ancestors);
                Collections.addAll(set2, ancestors);
                // 添加自身类别
                ancestorsList.add(diyAccessoriesCategoryMapper.selectOne(
                        new LambdaQueryWrapper<DiyAccessoriesCategory>()
                            .select(DiyAccessoriesCategory::getCategoryId)
                            .eq(DiyAccessoriesCategory::getAccessoriesId, v)).getCategoryId()
                    .toString());

                // 如果是基本类型，就不用理会
                //添加不兼容类别
                ancestorsList.stream().filter(ancestor -> Long.parseLong(ancestor) > 10)
                    .map(ancestor -> diyCategoryMapper.selectById(ancestor)
                        .getIncompatible()).filter(ObjectUtil::isNotNull)
                    .forEach(incompatibleCategories::addAll);
            }
        });

        List<DiyCategory> categories0 = diyCategoryMapper.selectList(
            // 获取cateId 下所有的类别
            new LambdaQueryWrapper<DiyCategory>()
                .apply(DataBaseHelper.findInSet(cateId, "ancestors")));

        // 排除只是单向添加不兼容的类别
        categories0.stream()
            .filter(diyCategory -> ObjectUtil.isNotNull(diyCategory.getIncompatible()))
            .filter(diyCategory -> diyCategory.getIncompatible().stream().anyMatch(set2::contains))
            .forEachOrdered(diyCategory -> {
                incompatibleCategories.addAll(
                    diyCategoryMapper.selectList(new LambdaQueryWrapper<DiyCategory>()
                            .apply(DataBaseHelper.findInSet(diyCategory.getId(), "ancestors")))
                        .stream().map(c -> c.getId().toString())
                        .collect(Collectors.toList()));
                incompatibleCategories.add(diyCategory.getId().toString());
            });

        List<DiyCategory> categories = diyCategoryMapper.selectList(
            // 获取cateId 下所有兼容的类别id
            new LambdaQueryWrapper<DiyCategory>()
                .select(DiyCategory::getId)
                .apply(DataBaseHelper.findInSet(cateId, "ancestors"))
                .notIn(incompatibleCategories.size() > 0, DiyCategory::getId,
                    incompatibleCategories)
                .notIn(incompatibleCategories.size() > 0, DiyCategory::getParentId,
                    incompatibleCategories));

        if (categories.size() == 0) {
            return null;
        }

        List<DiyAccessoriesCategory> diyAccessoriesCategories =
            diyAccessoriesCategoryMapper.selectList(
                // 获取配件id
                new LambdaQueryWrapper<DiyAccessoriesCategory>()
                    .in(categories.size() > 0,
                        DiyAccessoriesCategory::getCategoryId, categories
                            .stream()
                            .map(DiyCategory::getId)
                            .collect(Collectors.toList())));

        if (diyAccessoriesCategories.size() == 0) {
            return null;
        }

        List<Long> getAccessoriesIds = diyAccessoriesCategories
            .stream()
            .map(DiyAccessoriesCategory::getAccessoriesId)
            .collect(Collectors.toList());

        return diyAccessoriesMapper.selectList(
            new LambdaQueryWrapper<DiyAccessories>()
                .in(DiyAccessories::getId,
                    getAccessoriesIds));
    }

    @Override
    public R<priceResult> getPriceById(Long id) {
        return getPriceForList(baseMapper.selectById(id));
    }

    /**
     * 获取配件单价格
     *
     * @param diyAccessoriesList 配件单
     * @return
     */
    @SneakyThrows
    @Override
    public R<priceResult> getPriceForList(DiyAccessoriesList diyAccessoriesList) {
        Field[] fields = diyAccessoriesList.getClass().getDeclaredFields();
        List<String> diyAccessriesIds = new ArrayList<>();
        for (Field f : fields) {
            if (f.getType() == Long.class && !f.getName().equals("id")) {
                f.setAccessible(true);
                if (null != f.get(diyAccessoriesList)) {
                    diyAccessriesIds.add(f.get(diyAccessoriesList).toString());
                }
            }
        }
        Map<String, DiyAccessories> numberAccessMap = diyAccessriesIds.stream()
            .map(diyAccessoriesMapper::selectById)
            .collect(Collectors.toMap(DiyAccessories::getNumber, d -> d));
        List<String> list = new ArrayList<>(numberAccessMap.keySet());

        JSONObject json = new JSONObject();
        json.set("list", list);

        ObjectMapper mapper = new ObjectMapper();
        String url = urlConfig.getPriceurl() + "/getPriceByMap";

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(json.toString(), headers);

        try {
            String content = restTemplate.postForEntity(url, formEntity, String.class).getBody();
            R<priceResult> priceResultR = mapper.readValue(
                content,
                mapper.getTypeFactory().constructParametricType(R.class, priceResult.class));
            Map<String, Float> pricelist = priceResultR.getData().getPricelist();

            BigDecimal price = new BigDecimal(priceResultR.getData().getPriceSum());

            //异常配件
            List<String> numberList = pricelist.entrySet().stream()
                .filter(map -> map.getValue() < 1)
                .map(Entry::getKey).collect(Collectors.toList());
            for (String accNumber : numberList) {
                DiyAccessories diyAccessories = numberAccessMap.get(accNumber);
                price = price.add(diyAccessories.getPrice());
            }

            diyAccessoriesList.setPrice(price);
            baseMapper.updateById(diyAccessoriesList);
            updatePrice(pricelist, numberAccessMap);
            return priceResultR;
        } catch (JsonProcessingException e) {
            return R.fail("失败");
        }
    }

    /**
     * 通过用户id获取配件单
     *
     * @param userId
     * @return
     */
    @Override
    public List<DiyAccessoriesList> getDiyAccessoriesListByUserId(Long userId) {
        return baseMapper.getDiyUserAccessoriesListByUserId(userId);
    }

    /**
     * 通过用户id添加配件单
     *
     * @param diyAccessoriesList
     * @param userId
     * @return
     */
    @Override
    public Boolean addOrUpdateUserConfigs(DiyAccessoriesList diyAccessoriesList,
        Long userId) {
        if (ObjectUtil.isNotNull(diyAccessoriesList.getId())) {
            return baseMapper.updateById(diyAccessoriesList) > 0;
        }
        diyAccessoriesList.setType("1");
        int insert = baseMapper.insert(diyAccessoriesList);
        int insert1 = diyUserAccessoriesListMapper.insert(
            DiyUserAccessoriesList.builder().userId(userId)
                .AccessoriesListId(diyAccessoriesList.getId()).build());
        return insert1 > 0 && insert > 0;
    }

    @Override
    public boolean removeUserConfigs(String id, Long userId) {

        DiyUserAccessoriesList diyUserAccessoriesList = diyUserAccessoriesListMapper.selectOne(
            new LambdaQueryWrapper<DiyUserAccessoriesList>()
                .eq(DiyUserAccessoriesList::getUserId, userId)
                .eq(DiyUserAccessoriesList::getAccessoriesListId, id));
        if (ObjectUtil.isNotNull(diyUserAccessoriesList)) {
            return diyUserAccessoriesListMapper.deleteById(diyUserAccessoriesList) > 0;
        }
        return false;
    }

    @Async
    public void updatePrice(Map<String, Float> pricelist,
        Map<String, DiyAccessories> numberAccessMap) {
        for (String s : pricelist.keySet()) {
            DiyAccessories diyAccessories = numberAccessMap.get(s);
            if (diyAccessories.getNumber().equals("0")) {
                continue;
            }
            BigDecimal newPrice = new BigDecimal(Float.toString(pricelist.get(s)));
            if (newPrice.compareTo(BigDecimal.ZERO) < 1) {
                diyAccessories.setWarning("该商品可能被京东下架");
                diyAccessoriesMapper.updateById(diyAccessories);
            } else if (Math.abs(newPrice.compareTo(diyAccessories.getPrice())) > 1e-5) {
                diyAccessories.setWarning("");
                diyAccessories.setPrice(newPrice);
                diyAccessoriesMapper.updateById(diyAccessories);
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class priceResult {

        private Map<String, Float> pricelist;
        private String priceSum;
    }
}

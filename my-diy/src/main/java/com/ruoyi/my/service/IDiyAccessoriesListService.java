package com.ruoyi.my.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.my.domain.DiyAccessories;
import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.domain.vo.DiyAccessoriesListVo;
import com.ruoyi.my.domain.bo.DiyAccessoriesListBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import com.ruoyi.my.service.impl.DiyAccessoriesListServiceImpl.priceResult;
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

    /**
     * @param cateId             类比id
     * @param diyAccessoriesList 已经选择的配件
     * @return 当前cateId类别可以选择的配件
     */
    List<DiyAccessories> getCateAcce(Long cateId, DiyAccessoriesList diyAccessoriesList);

    /**
     * 把配件转为 k:配件id v:配件名 这种形式
     */
    Map<Long, DiyAccessories> getMap();


    R<priceResult> getPriceById(Long id);

    /**
     * 获取配件单的总价格
     *
     * @param diyAccessoriesList 配件单
     * @return 价格
     */
    R<priceResult> getPriceForList(DiyAccessoriesList diyAccessoriesList);

    /**
     * 通过用户id获取配件单
     *
     * @param userId
     * @return
     */
    List<DiyAccessoriesList> getDiyAccessoriesListByUserId(Long userId);

    /**
     * 通过用户id添加配件单
     *
     * @param diyAccessories
     * @param userId
     * @return
     */
    Boolean addOrUpdateUserConfigs(DiyAccessoriesList diyAccessories, Long userId);

    /**
     * 删除用户配件单
     *
     * @param id
     * @param userId
     * @return
     */
    boolean removeUserConfigs(String id, Long userId);
}

package com.ruoyi.my.service;

import com.ruoyi.my.domain.WxUser;
import com.ruoyi.my.domain.vo.WxUserVo;
import com.ruoyi.my.domain.bo.WxUserBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * wx 端用户信息Service接口
 *
 * @author ruoyi
 * @date 2022-11-22
 */
public interface IWxUserService {

    /**
     * 查询wx 端用户信息
     */
    WxUserVo queryById(Long userId);

    /**
     * 查询wx 端用户信息列表
     */
    TableDataInfo<WxUserVo> queryPageList(WxUserBo bo, PageQuery pageQuery);

    /**
     * 查询wx 端用户信息列表
     */
    List<WxUserVo> queryList(WxUserBo bo);

    /**
     * 新增wx 端用户信息
     */
    Boolean insertByBo(WxUserBo bo);

    /**
     * 修改wx 端用户信息
     */
    Boolean updateByBo(WxUserBo bo);

    /**
     * 校验并批量删除wx 端用户信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}

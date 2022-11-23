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
import com.ruoyi.my.domain.bo.WxUserBo;
import com.ruoyi.my.domain.vo.WxUserVo;
import com.ruoyi.my.domain.WxUser;
import com.ruoyi.my.mapper.WxUserMapper;
import com.ruoyi.my.service.IWxUserService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * wx 端用户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-22
 */
@RequiredArgsConstructor
@Service
public class WxUserServiceImpl implements IWxUserService {

    private final WxUserMapper baseMapper;

    /**
     * 查询wx 端用户信息
     */
    @Override
    public WxUserVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 查询wx 端用户信息列表
     */
    @Override
    public TableDataInfo<WxUserVo> queryPageList(WxUserBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<WxUser> lqw = buildQueryWrapper(bo);
        Page<WxUserVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询wx 端用户信息列表
     */
    @Override
    public List<WxUserVo> queryList(WxUserBo bo) {
        LambdaQueryWrapper<WxUser> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WxUser> buildQueryWrapper(WxUserBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<WxUser> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOpenId() != null, WxUser::getOpenId, bo.getOpenId());
        lqw.like(StringUtils.isNotBlank(bo.getNickName()), WxUser::getNickName, bo.getNickName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhonenumber()), WxUser::getPhonenumber, bo.getPhonenumber());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), WxUser::getAvatar, bo.getAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getIsInit()), WxUser::getIsInit, bo.getIsInit());
        return lqw;
    }

    /**
     * 新增wx 端用户信息
     */
    @Override
    public Boolean insertByBo(WxUserBo bo) {
        WxUser add = BeanUtil.toBean(bo, WxUser.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改wx 端用户信息
     */
    @Override
    public Boolean updateByBo(WxUserBo bo) {
        WxUser update = BeanUtil.toBean(bo, WxUser.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WxUser entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wx 端用户信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}

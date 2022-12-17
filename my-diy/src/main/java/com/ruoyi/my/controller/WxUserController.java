package com.ruoyi.my.controller;

import cn.hutool.core.io.FileUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.system.domain.vo.SysOssVo;
import com.ruoyi.system.service.ISysOssService;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.my.domain.vo.WxUserVo;
import com.ruoyi.my.domain.bo.WxUserBo;
import com.ruoyi.my.service.IWxUserService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * wx 端用户信息
 *
 * @author ruoyi
 * @date 2022-11-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wx/wxuser")
public class WxUserController extends BaseController {

    private final IWxUserService iWxUserService;


    /**
     * 查询wx 端用户信息列表
     */
    @SaCheckPermission("wx:wxuser:list")
    @GetMapping("/list")
    public TableDataInfo<WxUserVo> list(WxUserBo bo, PageQuery pageQuery) {
        return iWxUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出wx 端用户信息列表
     */
    @SaCheckPermission("wx:wxuser:export")
    @Log(title = "wx 端用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(WxUserBo bo, HttpServletResponse response) {
        List<WxUserVo> list = iWxUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "wx 端用户信息", WxUserVo.class, response);
    }

    /**
     * 获取wx 端用户信息详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("wx:wxuser:query")
    @GetMapping("/{userId}")
    public R<WxUserVo> getInfo(@NotNull(message = "主键不能为空")
    @PathVariable Long userId) {
        return R.ok(iWxUserService.queryById(userId));
    }

    /**
     * 新增wx 端用户信息
     */
    @SaCheckPermission("wx:wxuser:add")
    @Log(title = "wx 端用户信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WxUserBo bo) {
        return toAjax(iWxUserService.insertByBo(bo));
    }

    /**
     * 修改wx 端用户信息
     */
    @SaCheckPermission("wx:wxuser:edit")
    @Log(title = "wx 端用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WxUserBo bo) {
        return toAjax(iWxUserService.updateByBo(bo));
    }

    /**
     * 删除wx 端用户信息
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("wx:wxuser:remove")
    @Log(title = "wx 端用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
    @PathVariable Long[] userIds) {
        return toAjax(iWxUserService.deleteWithValidByIds(Arrays.asList(userIds), true));
    }


}

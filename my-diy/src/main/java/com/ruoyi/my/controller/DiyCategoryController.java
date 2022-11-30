package com.ruoyi.my.controller;

import cn.hutool.core.lang.tree.Tree;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.my.domain.DiyCategory;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.my.domain.vo.DiyCategoryVo;
import com.ruoyi.my.domain.bo.DiyCategoryBo;
import com.ruoyi.my.service.IDiyCategoryService;

/**
 * 配件类别
 *
 * @author ruoyi
 * @date 2022-11-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/category")
public class DiyCategoryController extends BaseController {

    private final IDiyCategoryService iDiyCategoryService;

    /**
     * 查询配件类别列表
     */
    @SaCheckPermission("system:category:list")
    @GetMapping("/list")
    public R<List<DiyCategoryVo>> list(DiyCategoryBo bo) {
        List<DiyCategoryVo> list = iDiyCategoryService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出配件类别列表
     */
    @SaCheckPermission("system:category:export")
    @Log(title = "配件类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(DiyCategoryBo bo, HttpServletResponse response) {
        List<DiyCategoryVo> list = iDiyCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "配件类别", DiyCategoryVo.class, response);
    }

    /**
     * 获取配件类别详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:category:query")
    @GetMapping("/{id}")
    public R<DiyCategoryVo> getInfo(@NotNull(message = "主键不能为空")
    @PathVariable Long id) {
        return R.ok(iDiyCategoryService.queryById(id));
    }

    /**
     * 新增配件类别
     */
    @SaCheckPermission("system:category:add")
    @Log(title = "配件类别", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DiyCategoryBo bo) {
        return toAjax(iDiyCategoryService.insertByBo(bo));
    }

    /**
     * 修改配件类别
     */
    @SaCheckPermission("system:category:edit")
    @Log(title = "配件类别", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DiyCategoryBo bo) {
        return toAjax(iDiyCategoryService.updateByBo(bo));
    }

    /**
     * 删除配件类别
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:category:remove")
    @Log(title = "配件类别", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
    @PathVariable Long[] ids) {
        return toAjax(iDiyCategoryService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}

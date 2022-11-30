package com.ruoyi.my.controller;

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
import com.ruoyi.my.domain.vo.DiyAccessoriesVo;
import com.ruoyi.my.domain.bo.DiyAccessoriesBo;
import com.ruoyi.my.service.IDiyAccessoriesService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 配件信息
 *
 * @author ruoyi
 * @date 2022-11-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/accessories")
public class DiyAccessoriesController extends BaseController {

    private final IDiyAccessoriesService iDiyAccessoriesService;

    /**
     * 查询配件信息列表
     */
    @SaCheckPermission("system:accessories:list")
    @GetMapping("/list")
    public TableDataInfo<DiyAccessoriesVo> list(DiyAccessoriesBo bo, PageQuery pageQuery) {
        return iDiyAccessoriesService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出配件信息列表
     */
    @SaCheckPermission("system:accessories:export")
    @Log(title = "配件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(DiyAccessoriesBo bo, HttpServletResponse response) {
        List<DiyAccessoriesVo> list = iDiyAccessoriesService.queryList(bo);
        ExcelUtil.exportExcel(list, "配件信息", DiyAccessoriesVo.class, response);
    }

    /**
     * 获取配件信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:accessories:query")
    @GetMapping("/{id}")
    public R<DiyAccessoriesVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iDiyAccessoriesService.queryById(id));
    }

    /**
     * 新增配件信息
     */
    @SaCheckPermission("system:accessories:add")
    @Log(title = "配件信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DiyAccessoriesBo bo) {
        return toAjax(iDiyAccessoriesService.insertByBo(bo));
    }

    /**
     * 修改配件信息
     */
    @SaCheckPermission("system:accessories:edit")
    @Log(title = "配件信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DiyAccessoriesBo bo) {
        return toAjax(iDiyAccessoriesService.updateByBo(bo));
    }

    /**
     * 删除配件信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:accessories:remove")
    @Log(title = "配件信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iDiyAccessoriesService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}

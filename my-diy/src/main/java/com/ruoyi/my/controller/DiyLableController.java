package com.ruoyi.my.controller;

import com.ruoyi.my.domain.DiyAccessories;
import com.ruoyi.my.domain.DiyLable;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
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
import com.ruoyi.my.domain.vo.DiyLableVo;
import com.ruoyi.my.domain.bo.DiyLableBo;
import com.ruoyi.my.service.IDiyLableService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标签管理
 *
 * @author ruoyi
 * @date 2022-12-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/lable")
public class DiyLableController extends BaseController {

    private final IDiyLableService iDiyLableService;

    /**
     * 查询标签管理列表
     */
    @SaCheckPermission("system:lable:list")
    @GetMapping("/list")
    public TableDataInfo<DiyLableVo> list(DiyLableBo bo, PageQuery pageQuery) {
        return iDiyLableService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出标签管理列表
     */
    @SaCheckPermission("system:lable:export")
    @Log(title = "标签管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(DiyLableBo bo, HttpServletResponse response) {
        List<DiyLableVo> list = iDiyLableService.queryList(bo);
        ExcelUtil.exportExcel(list, "标签管理", DiyLableVo.class, response);
    }

    /**
     * 获取标签管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:lable:query")
    @GetMapping("/{id}")
    public R<DiyLableVo> getInfo(@NotNull(message = "主键不能为空")
    @PathVariable Long id) {
        return R.ok(iDiyLableService.queryById(id));
    }

    /**
     * 新增标签管理
     */
    @SaCheckPermission("system:lable:add")
    @Log(title = "标签管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DiyLableBo bo) {
        bo.setType("0");
        return toAjax(iDiyLableService.insertByBo(bo));
    }

    /**
     * 修改标签管理
     */
    @SaCheckPermission("system:lable:edit")
    @Log(title = "标签管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DiyLableBo bo) {
        return toAjax(iDiyLableService.updateByBo(bo));
    }

    /**
     * 删除标签管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:lable:remove")
    @Log(title = "标签管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
    @PathVariable Long[] ids) {
        return toAjax(iDiyLableService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 获取标签map
     */
    @GetMapping("/map")
    public R<Map<Integer, DiyLable>> getMap() {
        return R.ok(iDiyLableService.getMap());
    }

}

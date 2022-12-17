package com.ruoyi.my.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.ruoyi.my.domain.DiyAccessories;
import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.service.impl.DiyAccessoriesListServiceImpl.priceResult;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.my.domain.vo.DiyAccessoriesListVo;
import com.ruoyi.my.domain.bo.DiyAccessoriesListBo;
import com.ruoyi.my.service.IDiyAccessoriesListService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 配件单
 *
 * @author ruoyi
 * @date 2022-12-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/accessoriesList")
public class DiyAccessoriesListController extends BaseController {

    private final IDiyAccessoriesListService iDiyAccessoriesListService;

    /**
     * 查询配件单k,v对应
     */
    @GetMapping("/map")
    public R<Map<Long, DiyAccessories>> getMap() {
        return R.ok(iDiyAccessoriesListService.getMap());
    }

    /**
     * 查询配件单列表
     */
    @SaCheckPermission("system:accessoriesList:list")
    @GetMapping("/list")
    public TableDataInfo<DiyAccessoriesListVo> list(DiyAccessoriesListBo bo, PageQuery pageQuery) {
        return iDiyAccessoriesListService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出配件单列表
     */
    @SaCheckPermission("system:accessoriesList:export")
    @Log(title = "配件单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(DiyAccessoriesListBo bo, HttpServletResponse response) {
        List<DiyAccessoriesListVo> list = iDiyAccessoriesListService.queryList(bo);
        ExcelUtil.exportExcel(list, "配件单", DiyAccessoriesListVo.class, response);
    }

    /**
     * 获取配件单详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:accessoriesList:query")
    @GetMapping("/{id}")
    public R<DiyAccessoriesListVo> getInfo(@NotNull(message = "主键不能为空")
    @PathVariable Long id) {
        return R.ok(iDiyAccessoriesListService.queryById(id));
    }

    /**
     * 新增配件单
     */
    @SaCheckPermission("system:accessoriesList:add")
    @Log(title = "配件单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DiyAccessoriesListBo bo) {
        bo.setType("0"); //设置为系统配置单
        bo.setLabel(bo.getLabel().stream().distinct().collect(Collectors.toList()));
        return toAjax(iDiyAccessoriesListService.insertByBo(bo));
    }

    /**
     * 修改配件单
     */
    @SaCheckPermission("system:accessoriesList:edit")
    @Log(title = "配件单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DiyAccessoriesListBo bo) {
        return toAjax(iDiyAccessoriesListService.updateByBo(bo));
    }

    /**
     * 删除配件单
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:accessoriesList:remove")
    @Log(title = "配件单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
    @PathVariable Long[] ids) {
        return toAjax(iDiyAccessoriesListService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 获取可以兼容的配件
     *
     * @param cateId
     * @param diyAccessoriesList
     * @return 可以兼容的配件列表
     */
    @PostMapping("/getCateAcce/{cateId}")
    public R<List<DiyAccessories>> getCateAcce(@PathVariable String cateId,
        @RequestBody DiyAccessoriesList diyAccessoriesList) {

        if (!Pattern.matches("^-?\\d+$", cateId)) {
            return R.warn("id错误");
        }
        long c = Long.parseLong(cateId);
        if (c >= 10) {
            return R.warn("获取商品类别错误");
        }
        return R.ok(iDiyAccessoriesListService.getCateAcce(c, diyAccessoriesList));
    }

    /**
     * 通过id查询配件单的总价格
     *
     * @return
     */
    @GetMapping("/getPriceById/{id}")
    public R<priceResult> getPriceById(@PathVariable String id) {
        if (!Pattern.matches("^-?\\d+$", id)) {
            return R.warn("id错误");
        }
        long c = Long.parseLong(id);
        return iDiyAccessoriesListService.getPriceById(c);
    }
}

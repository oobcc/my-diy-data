package com.ruoyi.my.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.my.domain.DiyAccessories;
import com.ruoyi.my.domain.DiyAccessoriesList;
import com.ruoyi.my.domain.WxUser;
import com.ruoyi.my.domain.dto.LoginDto;
import com.ruoyi.my.domain.vo.LoginVo;
import com.ruoyi.my.domain.vo.RecommendVo;
import com.ruoyi.my.domain.vo.WxUserVo;
import com.ruoyi.my.service.IDiyAccessoriesListService;
import com.ruoyi.my.service.IDiyCategoryService;
import com.ruoyi.my.service.IWxUserService;
import com.ruoyi.my.service.WxService;
import com.ruoyi.system.domain.vo.SysOssVo;
import com.ruoyi.system.service.ISysOssService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 对接 wx api
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/my/public")
public class wxApiController extends BaseController {


    private final WxService wxService;

    private final ISysOssService iSysOssService;

    private final IWxUserService iWxUserService;

    private final IDiyCategoryService iDiyCategoryService;

    private final IDiyAccessoriesListService iDiyAccessoriesListService;

    /**
     * 小程序获取用户信息
     *
     * @param code
     * @return Object
     */
    @SaIgnore
    @PostMapping("login")
    public R<LoginVo> login(@RequestBody LoginDto code) {
        return wxService.login(code.getCode());
    }

    @GetMapping("checkLogin")
    public R<Void> checkLogin() {
        System.out.println(getUserId());
        return R.ok("登录中");
    }

    /**
     * 修改微信头像
     *
     * @param avatarfile
     * @return
     */
    @PostMapping("/avatar")
    public R<Map<String, Object>> avatar(@RequestPart("avatarfile") MultipartFile avatarfile) {
        Map<String, Object> ajax = new HashMap<>();
        if (!avatarfile.isEmpty()) {
            String extension = FileUtil.extName(avatarfile.getOriginalFilename());
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return R.fail(
                    "文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
            SysOssVo oss = iSysOssService.upload(avatarfile);
            String avatar = oss.getUrl();
            if (iWxUserService.updateUserAvatar(getUserId(), oss.getUrl())) {
                ajax.put("imgUrl", avatar);
                return R.ok(ajax);
            }
        }
        return R.fail("上传图片异常，请联系管理员");
    }

    @GetMapping("/getUserMsg")
    public R<WxUserVo> getUserMsg() {
        Long userId = getUserId();
        WxUserVo userMsgById = iWxUserService.getUserMsgById(userId);
        if (userMsgById != null) {
            return R.ok(userMsgById);
        } else {
            return R.fail("未知错误，请联系管理员");
        }
    }

    @PutMapping("/updateUserName")
    public R<WxUser> updateUserName(@RequestParam("name") String name) {
        if (!ObjectUtil.isNotNull(name)) {
            return R.fail("用户名不能为空");
        }
        Long userId = getUserId();
        return R.ok(iWxUserService.updateUserNameByLoginId(userId, name));
    }

    /**
     * 通过登录 token 获取用户配件单
     *
     * @return 用户配置单
     */
    @GetMapping("getUserAccessoriesList")
    public R<List<DiyAccessoriesList>> getDiyAccessoriesListByLogin() {
        Long userId = getUserId();
        List<DiyAccessoriesList> diyAccessoriesLists =
            iDiyAccessoriesListService.getDiyAccessoriesListByUserId(
                userId);
        return R.ok(diyAccessoriesLists);
    }


    /**
     * 通过登录 token 添加/更新用户配件单
     *
     * @return 是非添加成功
     */
    @PostMapping("addOrUpdateUserConfigs")
    public R<Boolean> addOrUpdateUserConfigs(
        @RequestBody DiyAccessoriesList diyAccessoriesList) {
        if (iDiyAccessoriesListService.addOrUpdateUserConfigs(diyAccessoriesList,
            getUserId())) {
            return R.ok();
        }
        return R.fail();
    }

    /**
     * @param id 配件单id
     * @return 是非删除成功
     */
    @DeleteMapping("removeUserConfigs/{id}")
    public R<Boolean> removeUserConfigs(@PathVariable String id) {
        if (iDiyAccessoriesListService.removeUserConfigs(id,
            getUserId())) {
            return R.ok();
        }
        return R.fail();
    }

    /**
     * 推荐
     *
     * @param text 用户发的消息
     * @return 标签数组，配置单
     */
    @GetMapping("/recommend")
    public R<RecommendVo> getRecommendFormText(@RequestParam String text) {
        return R.ok(wxService.recommended(text));
    }


}

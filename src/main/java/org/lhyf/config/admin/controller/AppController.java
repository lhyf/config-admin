package org.lhyf.config.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.lhyf.config.constant.WebConstant;
import org.lhyf.config.model.Bo.RestResponseBo;
import org.lhyf.config.model.Vo.SimpleAppVo;
import org.lhyf.config.pojo.App;
import org.lhyf.config.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-13 14:35
 * @desc AppPropertiesController
 * 管理平台管理相关配置
 **/
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;

    @GetMapping("/config")
    public String index() {

        return "admin/config";
    }

    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Model model) {
        PageHelper.startPage(page, 20);
        List<App> list = appService.getAllApp();
        PageInfo<App> info = new PageInfo<>(list);
        model.addAttribute("apps", info);
        return "admin/apps";
    }

    /**
     * 添加APP
     * @param code
     * @param name
     * @param intro
     * @return
     */
    @ResponseBody
    @PostMapping("/add-app")
    public RestResponseBo addApp(String code, String name, String intro) {

        List<App> apps = appService.getAppByCode(code);
        if(apps != null && apps.size() > 0){
            return RestResponseBo.fail("AppCode: " + code + " 已存在!");
        }

        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);
        appService.save(code, name, intro, username);

        return RestResponseBo.ok();
    }

    @ResponseBody
    @PostMapping("/update-app")
    public RestResponseBo updateApp(Integer id,String name,String intro){
        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);
        appService.updateApp(id,name,intro,username);
        return RestResponseBo.ok();
    }

    @ResponseBody
    @PostMapping("/delete-app")
    public RestResponseBo deleteApp(Integer id){
        appService.deleteAppById(id);
        return RestResponseBo.ok();
    }

    /**
     * 页面获取App的下来列表
     * @return
     */
    @ResponseBody
    @PostMapping("/get-all-app")
    public RestResponseBo<List<SimpleAppVo>> getAllApp(){
        List<SimpleAppVo> list = new ArrayList<>();
        List<App> apps = appService.getAllApp();

        SimpleAppVo appVo;
        for(App app:apps){
            appVo = new SimpleAppVo();
            appVo.setId(app.getId());
            appVo.setCode(app.getAppCode());
            appVo.setName(app.getAppName());
            list.add(appVo);
        }
        return RestResponseBo.ok(list);
    }
}

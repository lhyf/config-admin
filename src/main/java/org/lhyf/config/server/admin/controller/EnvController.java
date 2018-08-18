package org.lhyf.config.server.admin.controller;

import org.lhyf.config.server.constant.WebConstant;
import org.lhyf.config.server.model.Bo.RestResponseBo;
import org.lhyf.config.server.model.Vo.EnvVo;
import org.lhyf.config.server.model.Vo.NameSpaceVo;
import org.lhyf.config.server.model.Vo.SimpleEnvVo;
import org.lhyf.config.server.pojo.TEnv;
import org.lhyf.config.server.service.EnvService;
import org.lhyf.config.server.service.NameSpaceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @author YF
 * @date 2018-07-13 18:56
 * @desc EnvController
 *
 **/
@Controller
@RequestMapping("/env")
public class EnvController {

    @Autowired
    private EnvService envService;

    @Autowired
    private NameSpaceService nameSpaceService;

    @GetMapping("/list")
    public String list(){
        return "admin/envs";
    }

    @ResponseBody
    @RequestMapping("/add-env")
    public RestResponseBo save(String name,String intro,String appCode){
        List<TEnv> list =  envService.selectEnvByNameAndAppcode(name,appCode);
        if(list != null && list.size() > 0){
            return RestResponseBo.fail("环境名重复!");
        }

        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);
        envService.save(name,intro,appCode,username);
        return RestResponseBo.ok();
    }


    /**
     * 下拉框中获取环境列表
     * @param appCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/get-app-env")
    public RestResponseBo<List<SimpleEnvVo>> getEnvVoByAppcode(String appCode){
        List<SimpleEnvVo> list = envService.getEnvNameByAppcode(appCode);
        return RestResponseBo.ok(list);
    }

    @ResponseBody
    @PostMapping("get-env-namespace")
    public RestResponseBo getEnvNameSpace(Integer envId){
        List<NameSpaceVo> nsVo = nameSpaceService.getEnvNameSpaceByEnvId(envId);
        return RestResponseBo.ok(nsVo);
    }

    /**
     * 通过环境id 直接获取该环境下所有配置项
     * @return
     */
    @ResponseBody
    @RequestMapping("/get-env-item")
    public RestResponseBo getEnvItem(Integer envId){
        EnvVo env = envService.getEnvVOItem(envId);
        return RestResponseBo.ok(env);
    }

    /**
     * 环境管理页面,获取环境列表
     * @param appCode
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("/get-env")
    public RestResponseBo getEnvByAppcode(String appCode ,@RequestParam(value = "page", defaultValue = "1", required = false) int page){
        PageHelper.startPage(page,20);
        List<TEnv> list = envService.getEnvByAppcode(appCode);
        PageInfo info = new PageInfo(list);
        return RestResponseBo.ok(info);
    }

    @ResponseBody
    @PostMapping("/update-env")
    public RestResponseBo update(Integer id,String name,String intro){
        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);
        envService.updateEnv(id,name,intro,username);
        return RestResponseBo.ok();
    }

    /**
     * 删除环境，同时删除环境下的其他关联项
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/delete-env")
    public RestResponseBo delete(Integer id){
        envService.deleteEnvAndNsById(id);
        return RestResponseBo.ok();
    }
}

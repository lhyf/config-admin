package org.lhyf.config.server.admin.controller;

import org.lhyf.config.server.constant.WebConstant;
import org.lhyf.config.server.model.Bo.RestResponseBo;
import org.lhyf.config.server.pojo.TNamespace;
import org.lhyf.config.server.service.ItemService;
import org.lhyf.config.server.service.NameSpaceService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/****
 * @author YF
 * @date 2018-07-14 16:51
 * @desc NameSpaceController
 *
 **/
@Controller
@RequestMapping("/namespace")
public class NameSpaceController {

    @Autowired
    private NameSpaceService nameSpaceService;

    @Autowired
    private ItemService itemService;

    @ResponseBody
    @PostMapping("/add-ns")
    public RestResponseBo addNameSpace(String code, String name, String intro, Integer envId) {
        List<TNamespace> list = nameSpaceService.getEnvNameSpaceByEnvIdAndCode(envId,code);
        if(list != null && list.size() > 0){
            return RestResponseBo.fail("当前环境下code: " + code + " 已存在!");
        }
        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);
        nameSpaceService.save(code, name, intro, envId, username);
        return RestResponseBo.ok();
    }


    @ResponseBody
    @PostMapping("/delete-namespace")
    public RestResponseBo deleteNamespace(Integer id){
        // 1. 删除与namespace关联的item
        itemService.deleteItemByNsId(id);
        // 2. 删除namespace
        nameSpaceService.deleteNsById(id);
        return RestResponseBo.ok();
    }

    @ResponseBody
    @PostMapping("/get-namespace-by-id")
    public RestResponseBo<TNamespace> getNamespaceById(Integer id){
        TNamespace namespace = nameSpaceService.getNamespaceById(id);
        return RestResponseBo.ok(namespace);
    }

    /**
     * 更新namespace
     * @param id
     * @param code
     * @param name
     * @param intro
     * @return
     */
    @ResponseBody
    @PostMapping("/update-namespace")
    public RestResponseBo updateNameSpace(Integer id,String code ,String name,String intro,Integer envId){
        List<TNamespace> list = nameSpaceService.getNamespaceByEnvIdAndCode(envId,code);
        if(list != null && list.size() > 0){
            for(TNamespace ns: list){
                if(ns.getId() != id){
                    return RestResponseBo.fail(code + " 重复!");
                }
            }
        }

        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);

        nameSpaceService.updateNameSpace(id,code,name,intro,username);
        return RestResponseBo.ok();
    }
}

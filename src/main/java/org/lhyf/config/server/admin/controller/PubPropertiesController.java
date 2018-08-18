package org.lhyf.config.server.admin.controller;

import org.lhyf.config.server.constant.WebConstant;
import org.lhyf.config.server.model.Bo.RestResponseBo;
import org.lhyf.config.server.model.Vo.PubNameSpaceVo;
import org.lhyf.config.server.model.Vo.SimplePubNamespaceVo;
import org.lhyf.config.server.pojo.TPubItem;
import org.lhyf.config.server.pojo.TPubNamespace;
import org.lhyf.config.server.service.PubItemService;
import org.lhyf.config.server.service.PubNamespaceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-12 14:18
 * @desc CommPropertiesController
 *
 **/
@Controller
@RequestMapping("/public")
public class PubPropertiesController {

    @Autowired
    private PubNamespaceService pubNamespaceService;

    @Autowired
    public PubItemService pubItemService;

    @GetMapping("/index")
    public String index() {

        return "/admin/public/index";
    }


    /**
     * 删除某一项配置
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/delete-pub-item")
    public RestResponseBo deletePubItem(Integer id){
        pubItemService.deletePubItem(id);
        return RestResponseBo.ok();
    }
    /**
     * 更新公共namespace下某个配置项
     * @param id
     * @param property
     * @param value
     * @param intro
     * @return
     */
    @ResponseBody
    @PostMapping("/update-pub-item")
    public RestResponseBo updatePubItem(Integer id,String property,String value,String intro){
        // 1. 通过property查询是否property重复,但不应该包括本身的property
        List<TPubItem> list = pubItemService.getPubItemsByProperty(property);
        if(list!=null&& list.size() > 0){
            // 防止因为存在多个重复 property ,所以只能都比较一遍id是否相等
            for(TPubItem item:list){
                if(item.getId() != id){
                    return RestResponseBo.fail(property + "重复!");
                }
            }
        }

        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);
        pubItemService.update(id,property,value,intro,username);
        return RestResponseBo.ok();
    }

    /**
     * 保存公共namespace 下的配置项
     * @param property
     * @param value
     * @param intro
     * @param namespaceId
     * @return
     */
    @ResponseBody
    @PostMapping("/save-pub-item")
    public RestResponseBo savePubItem(String property,String value,String intro,Integer namespaceId){

        List<TPubItem> list = pubItemService.selectItemByNSIdAndProperty(namespaceId,property);
        if(list!=null && list.size() > 0){
            return RestResponseBo.fail("property: " + property + " 重复!");
        }

        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getSession().getAttribute(WebConstant.SESSION_USERNAME);
        int i = pubItemService.save(property,value,intro,namespaceId,username);
        return RestResponseBo.ok();
    }

    /**
     * 通过公共namespace id 获取其下所有配置
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/get-pub-namespace-properties")
    public RestResponseBo<List<TPubItem>> getPropertiesByNameSpaceId(Integer id){
        List<TPubItem> items = pubItemService.findIteamByNameSpaceId(id);
        return RestResponseBo.ok(items);
    }

    /**
     * 保存页面添加的 公共 namespace
     * @param code
     * @param name
     * @param intro
     * @return
     */
    @ResponseBody
    @PostMapping("/save-pub-namespace")
    public RestResponseBo savePubNameSpace(String code, String name, String intro) {

        // 1. 查看code是否重复
        List<TPubNamespace> list = pubNamespaceService.getPubNameSpaceByCode(code);
        if(list != null && list.size() > 0){
            return RestResponseBo.fail(code + "重复!");
        }

        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getSession().getAttribute(WebConstant.SESSION_USERNAME);

        int i = pubNamespaceService.save(code, name, intro,username);
        return RestResponseBo.ok();
    }

    /**
     * 获取下拉列表中的公共namespace
     * @return
     */
    @ResponseBody
    @PostMapping("/get-all-pub-namespace")
    public RestResponseBo<List<SimplePubNamespaceVo>> getAllPubNameSpace(){
        List<SimplePubNamespaceVo> list = new ArrayList<>();
        List<TPubNamespace> namespaces = pubNamespaceService.findAll();

        SimplePubNamespaceVo vo;
        for(TPubNamespace namespace:namespaces){
            vo = new SimplePubNamespaceVo();
            vo.setId(namespace.getId());
            vo.setName(namespace.getName());
            vo.setSpaceCode(namespace.getSpaceCode());
            list.add(vo);
        }
        return RestResponseBo.ok(list);
    }


    @ResponseBody
    @PostMapping("/get-all-pubnamespace-whit-item")
    public RestResponseBo getPubNameSpaceWithItem(){
        List<PubNameSpaceVo> list = pubNamespaceService.getPubNameSpaceWithItem();
        return RestResponseBo.ok(list);
    }

    @ResponseBody
    @PostMapping("/delete-pub-namespace")
    public RestResponseBo deletePubNameSpace(Integer id){

        // 1. 先删除namespace下的item
        pubItemService.deletePubItemByNameSpaceId(id);
        // 2. 删除namespace
        pubNamespaceService.deleteNameSpaceById(id);
        return RestResponseBo.ok();
    }

    @ResponseBody
    @PostMapping("/get-pub-namespace-by-id")
    public RestResponseBo<TPubNamespace> getPubNameSpaceById(Integer id){
        TPubNamespace ns = pubNamespaceService.getPubNameSpaceById(id);
        return RestResponseBo.ok(ns);
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
    @PostMapping("/update-pub-namespace")
    public RestResponseBo updatePubNamespace(Integer id,String code,String name,String intro){

        // 1. 查看code是否重复
        List<TPubNamespace> list = pubNamespaceService.getPubNameSpaceByCode(code);
        if(list != null && list.size() > 0 ){
            // 防止因为存在多个重复 property ,所以只能都比较一遍id是否相等
            for(TPubNamespace ns:list){
                if(ns.getId() != id){
                    return RestResponseBo.fail(code + "重复!");
                }
            }
        }

        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);
        int i = pubNamespaceService.updatePubNamespace(id,code,name,intro,username);
        return RestResponseBo.ok();
    }
}

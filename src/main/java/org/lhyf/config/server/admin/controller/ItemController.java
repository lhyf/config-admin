package org.lhyf.config.server.admin.controller;

import org.lhyf.config.server.constant.WebConstant;
import org.lhyf.config.server.model.Bo.RestResponseBo;
import org.lhyf.config.server.model.Vo.EnvNameSpaceVo;
import org.lhyf.config.server.model.Vo.NameSpaceItemVo;
import org.lhyf.config.server.model.Vo.NameSpaceVo;
import org.lhyf.config.server.pojo.TItem;
import org.lhyf.config.server.pojo.TNamespace;
import org.lhyf.config.server.pojo.TPubItem;
import org.lhyf.config.server.pojo.TPubNamespace;
import org.apache.shiro.SecurityUtils;
import org.lhyf.config.server.service.ItemService;
import org.lhyf.config.server.service.NameSpaceService;
import org.lhyf.config.server.service.PubItemService;
import org.lhyf.config.server.service.PubNamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/****
 * @author YF
 * @date 2018-07-14 17:26
 * @desc ItemController
 *
 **/
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private PubItemService pubItemService;

    @Autowired
    private NameSpaceService nameSpaceService;

    @Autowired
    private PubNamespaceService pubNamespaceService;

    @ResponseBody
    @PostMapping("/add-item")
    public RestResponseBo addItem(Integer envId , Integer nsid, String property, String value, String intro) {

        // 查询当前环境下是否存在重复的property
        List<TItem> list = itemService.selectItemByEnvIdAndProperty(envId,property);
        if (list != null && list.size() > 0) {
            return RestResponseBo.fail("当前环境下" + property + " 已存在!");
        }

        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);
        itemService.save(property, value, intro, nsid, username);
        return RestResponseBo.ok();
    }

    /**
     * create
     * 从公开namespace导入namespace和item
     *
     * 参数格式: {"envId":"6","ns":[{"id":"10","item":[23,25]}]}
     * envId: 拷入环境id
     * ns: 公共ns
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add-pub-namesapce-item-pri", consumes = "application/json")
    public RestResponseBo addPubNameSpaceAndItem(@RequestBody EnvNameSpaceVo vo) {
        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);

        // 1. 通过envId 获取其下所有namespace列表
        List<NameSpaceVo> nameSpaceVos = nameSpaceService.getEnvNameSpaceByEnvId(vo.getEnvId());

        // 2. 通过传入的nsid,获取对应pubNamespace 对象
        List<Integer> nsIdList = new ArrayList<>();
        for (NameSpaceItemVo ns : vo.getNs()) {
            nsIdList.add(ns.getId());
        }
        List<TPubNamespace> pubNamespaceList = new ArrayList<>();
        if (nsIdList.size() > 0) {
            pubNamespaceList = pubNamespaceService.getPubNameSpaceByIds(nsIdList);
        }

        // 3. 比较 1 2 ,如果不存在重复的ns code ,则执行ns的拷贝,否则跳过,跳过的记录code名
        boolean copyns;
        StringBuffer sb = new StringBuffer("重复项Namespace code:");
        for (TPubNamespace pubns : pubNamespaceList){
            copyns = true;
            for(NameSpaceVo priNs:nameSpaceVos){
                if(pubns.getSpaceCode().equalsIgnoreCase(priNs.getSpaceCode())){
                    sb.append(pubns.getSpaceCode()).append("|");
                    copyns = false;
                    break;
                }

            }
            // 执行拷贝
            if(copyns){
                int newNsId = nameSpaceService.copyPubNsToPriNs(pubns,vo.getEnvId(),username);
                for(NameSpaceItemVo ns :vo.getNs()){
                    if(ns.getId().intValue() == pubns.getId().intValue()){
                        // 4. 不跳过的公共ns,获取上传的所有item,执行拷贝
                        int i = itemService.copyPubItemListToPriItem(newNsId,ns.getItem(),username);
                    }
                }

            }

        }
        String msg = "";
        if(sb.length() > 18){
            msg = sb.substring(0,sb.length()-1) + " 将被跳过!";
        }
        return RestResponseBo.ok(msg);
    }

    /**
     * create
     * 创建新的namespace
     * 从其他环境的namespace导入namespace和item
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add-env-namesapce-item-pri", consumes = "application/json")
    public RestResponseBo addOtherEnvNameSpaceAndItem(@RequestBody EnvNameSpaceVo vo) {
        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);

        // 1. 通过envId 获取其下所有namespace列表
        List<NameSpaceVo> nameSpaceVos = nameSpaceService.getEnvNameSpaceByEnvId(vo.getEnvId());

        // 2. 通过传入的nsid,获取对应Namespace 对象
        List<Integer> nsIdList = new ArrayList<>();
        for (NameSpaceItemVo ns : vo.getNs()) {
            nsIdList.add(ns.getId());
        }
        List<TNamespace> pubNamespaceList = new ArrayList<>();
        if (nsIdList.size() > 0) {
            pubNamespaceList = nameSpaceService.getNameSpaceByIds(nsIdList);
        }

        // 3. 比较 1 2 ,如果不存在重复的ns code ,则执行ns的拷贝,否则跳过,跳过的记录code名
        boolean copyns;
        StringBuffer sb = new StringBuffer("重复项Namespace code:");
        for (TNamespace ns : pubNamespaceList){
            copyns = true;
            for(NameSpaceVo priNs:nameSpaceVos){
                if(ns.getSpaceCode().equalsIgnoreCase(priNs.getSpaceCode())){
                    sb.append(ns.getSpaceCode()).append("|");
                    copyns = false;
                    break;
                }

            }
            // 执行拷贝
            if(copyns){
                int newNsId = nameSpaceService.copyNamespace(ns,vo.getEnvId(),username);
                for(NameSpaceItemVo n :vo.getNs()){
                    if(n.getId().intValue() == ns.getId().intValue()){
                        // 4. 不跳过的ns,获取上传的所有item,执行拷贝
                        int i = itemService.copyItemByItemId(n.getItem(),newNsId,username);
                    }
                }
            }

        }
        String msg = "";
        if(sb.length() > 18){
            msg = sb.substring(0,sb.length()-1) + " 将被跳过!";
        }
        return RestResponseBo.ok(msg);
    }

    /**
     * 将公共配置中的配置项导入到私有namespace中
     *
     * @param nsId      私有的namespaceID
     * @param pubItemId 公共配置项的id数组
     * @return
     */
    @ResponseBody
    @PostMapping("add-pub-item-to-pri")
    public RestResponseBo addPubItem(Integer nsId, @RequestParam("pubItemId[]") Integer[] pubItemId) {
        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);

        StringBuffer sb = new StringBuffer("重复项:");
        boolean copy = true;

        // 1. 使用nsId 查询其下所有的配置项
        List<TItem> items = itemService.getAllItemByNameSpaceId(nsId);
        // 2. 使用传入的公共配置项ID 查询
        List<TPubItem> pubItems = pubItemService.getPubItemsByIds(pubItemId);
        // 3. 使用1,2 查询到的两个配置项列表对比是否存下相同的property,如果存在则记录,
        for (TPubItem pubI : pubItems) {
            copy = true;
            for (TItem pri : items) {
                // 4. 存在相同property的项将跳过.
                if (pri.getProperty().equalsIgnoreCase(pubI.getProperty())) {
                    // 记录下当前property 然后跳出当前循环
                    sb.append(pri.getProperty()).append("|");
                    copy = false;
                    break;
                }
            }
            // 5. 拷贝存储配置项
            if (copy) {
                itemService.copyPubItemToPriItem(pubI, nsId, username);
            }
        }
        String msg = "";
        if (sb.length() > 4) {
            msg = sb.substring(0, sb.length() - 1) + "将被跳过!";
        }
        return RestResponseBo.ok(msg);
    }

    /**
     * 将当前项目中其他环境下的配置项导入到namespace中
     *
     * @param nsId      私有的namespaceID
     * @param itemId    其他环境下item的id数组
     * @return
     */
    @ResponseBody
    @PostMapping("add-env-item-to-pri")
    public RestResponseBo addEnvItem(Integer nsId, @RequestParam("itemId[]") Integer[] itemId) {
        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);

        StringBuffer sb = new StringBuffer("重复项:");
        boolean copy = true;

        // 1. 使用nsId 查询其下所有的配置项
        List<TItem> items = itemService.getAllItemByNameSpaceId(nsId);
        // 2. 使用传入的公共配置项ID 查询
        List<TItem> otherItems = itemService.getItemByIds(itemId);
        // 3. 使用1,2 查询到的两个配置项列表对比是否存下相同的property,如果存在则记录,
        for (TItem item : otherItems) {
            copy = true;
            for (TItem pri : items) {
                // 4. 存在相同property的项将跳过.
                if (pri.getProperty().equalsIgnoreCase(item.getProperty())) {
                    // 记录下当前property 然后跳出当前循环
                    sb.append(pri.getProperty()).append("|");
                    copy = false;
                    break;
                }
            }
            // 5. 拷贝存储配置项
            if (copy) {
                itemService.copyItem(item, nsId, username);
            }
        }
        String msg = "";
        if (sb.length() > 4) {
            msg = sb.substring(0, sb.length() - 1) + "将被跳过!";
        }
        return RestResponseBo.ok(msg);
    }


    /**
     * 更新 item
     *
     * @param id
     * @param property
     * @param value
     * @param intro
     * @return
     */
    @ResponseBody
    @PostMapping("/update-item")
    public RestResponseBo updateItem(Integer envId,Integer id, String property, String value, String intro) {

        // 查询当前环境下是否存在重复的property
        List<TItem> list = itemService.selectItemByEnvIdAndProperty(envId,property);
        if (list != null && list.size() > 0) {
            // 防止因为存在多个重复 property ,所以只能都比较一遍id是否相等
            for (TItem item : list) {
                if (item.getId().intValue() != id.intValue()) {
                    return RestResponseBo.fail(property + "重复!");
                }
            }
        }

        String username = (String) SecurityUtils.getSubject().getSession().getAttribute(WebConstant.SESSION_USERNAME);
        itemService.updateItem(id, property, value, intro, username);
        return RestResponseBo.ok();
    }

    @ResponseBody
    @PostMapping("/delete-item")
    public RestResponseBo deleteItem(Integer id) {
        int i = itemService.deleteItemById(id);
        return RestResponseBo.ok();
    }
}

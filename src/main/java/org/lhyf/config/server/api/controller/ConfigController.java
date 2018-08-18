package org.lhyf.config.server.api.controller;

import org.lhyf.config.server.pojo.TItem;
import org.lhyf.config.server.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/****
 * @author YF
 * @date 2018-07-09 18:31
 * @desc ConfigController
 *
 **/
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ItemService itemService;

    @PostMapping("/{appcode}/{envname}/{nscode}")
    public Map<String, String> getConfig(@PathVariable("appcode") String appcode,
                                         @PathVariable("envname") String envname,
                                         @PathVariable("nscode") String nscode) {
        logger.info("获取配置 App Code:{}, 环境名:{},Namespace Code:{}.",appcode ,envname ,nscode);
        List<TItem> list = itemService.selectItemByAppCodeAndEnvNameAndNScode(appcode,envname,nscode);
        Map<String, String> map = new LinkedHashMap<>();
        for(TItem i:list){
            map.put(i.getProperty(),i.getValue());
        }
        logger.info("配置参数:{}",map);
        return map;
    }

    @PostMapping("/{appcode}/{envname}")
    public Map<String, String> getConfig(@PathVariable("appcode") String appcode,
                                         @PathVariable("envname") String envname
                                         ) {
        logger.info("获取配置 App Code:{}, 环境名:{}.",appcode ,envname );
        List<TItem> list = itemService.selectItemByAppCodeAndEnvName(appcode,envname);
        Map<String, String> map = new LinkedHashMap<>();
        for(TItem i:list){
            map.put(i.getProperty(),i.getValue());
        }
        logger.info("配置参数:{}",map);
        return map;
    }

}

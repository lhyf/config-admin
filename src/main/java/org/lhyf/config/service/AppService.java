package org.lhyf.config.service;

import org.lhyf.config.mapper.AppMapper;
import org.lhyf.config.model.Vo.NameSpaceVo;
import org.lhyf.config.model.Vo.SimpleEnvVo;
import org.lhyf.config.pojo.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-13 18:15
 * @desc AppService
 *
 **/

@Service
public class AppService {
    @Autowired
    private AppMapper appMapper;

    @Autowired
    private EnvService envService;

    @Autowired
    private NameSpaceService nameSpaceService;

    @Autowired
    private ItemService itemService;

    public List<App> getAllApp(){
        List<App> apps = appMapper.selectByExample(null);
        return apps;
    }

    public int save(String code,String name,String intro,String username){

        App app = new App();
        app.setAppCode(code);
        app.setAppName(name);
        app.setCreateBy(username);
        app.setCreateTime(new Date());
        app.setUpdateBy(username);
        app.setUpdateTime(new Date());
        app.setIntro(intro);
        return appMapper.insertSelective(app);
    }

    public List<App> getAppByCode(String code) {
        Example example = new Example(App.class);
        example.createCriteria().andEqualTo("code",code);
        return appMapper.selectByExample(example);
    }

    public int updateApp(Integer id,String name, String intro,String username) {
        App app = new App();
        app.setId(id);
        app.setAppName(name);
        app.setIntro(intro);
        app.setUpdateBy(username);
        app.setUpdateTime(new Date());
        return appMapper.updateByPrimaryKeySelective(app);
    }

    public App getAppCodeById(Integer id){
        App app = appMapper.selectByPrimaryKey(id);
        return app;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteAppById(Integer id) {
        App app = getAppCodeById(id);
        List<SimpleEnvVo> envs = envService.getEnvNameByAppcode(app.getAppCode());
        for(SimpleEnvVo env:envs){
            List<NameSpaceVo> nsList = nameSpaceService.getEnvNameSpaceByEnvId(env.getId());
            for(NameSpaceVo ns:nsList){
                itemService.deleteItemByNsId(ns.getId());
                nameSpaceService.deleteNsById(ns.getId());
            }
            envService.deleteEnvById(env.getId());
        }
        return appMapper.deleteByPrimaryKey(id);
    }
}

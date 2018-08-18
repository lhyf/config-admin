package org.lhyf.config.server.service;

import org.lhyf.config.server.mapper.TAppMapper;
import org.lhyf.config.server.model.Vo.NameSpaceVo;
import org.lhyf.config.server.model.Vo.SimpleEnvVo;
import org.lhyf.config.server.pojo.TApp;
import org.lhyf.config.server.pojo.TAppExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private TAppMapper appMapper;

    @Autowired
    private EnvService envService;

    @Autowired
    private NameSpaceService nameSpaceService;

    @Autowired
    private ItemService itemService;

    public List<TApp> getAllApp(){
        List<TApp> apps = appMapper.selectByExample(null);
        return apps;
    }

    public int save(String code,String name,String intro,String username){

        TApp app = new TApp();
        app.setAppCode(code);
        app.setAppName(name);
        app.setCreateBy(username);
        app.setCreateTime(new Date());
        app.setUpdateBy(username);
        app.setUpdateTime(new Date());
        app.setIntro(intro);
        return appMapper.insertSelective(app);
    }

    public List<TApp> getAppByCode(String code) {
        TAppExample example = new TAppExample();
        example.createCriteria().andAppCodeEqualTo(code);
        return appMapper.selectByExample(example);
    }

    public int updateApp(Integer id,String name, String intro,String username) {
        TApp app = new TApp();
        app.setId(id);
        app.setAppName(name);
        app.setIntro(intro);
        app.setUpdateBy(username);
        app.setUpdateTime(new Date());
        return appMapper.updateByPrimaryKeySelective(app);
    }

    public TApp getAppCodeById(Integer id){
        TApp app = appMapper.selectByPrimaryKey(id);
        return app;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteAppById(Integer id) {
        TApp app = getAppCodeById(id);
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

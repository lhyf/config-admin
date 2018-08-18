package org.lhyf.config.server.service;

import org.lhyf.config.server.mapper.TEnvMapper;
import org.lhyf.config.server.model.Vo.EnvVo;
import org.lhyf.config.server.model.Vo.NameSpaceVo;
import org.lhyf.config.server.model.Vo.SimpleEnvVo;
import org.lhyf.config.server.pojo.TEnv;
import org.lhyf.config.server.pojo.TEnvExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-13 18:59
 * @desc EnvService
 *
 **/
@Service
public class EnvService {
    @Autowired
    private TEnvMapper envMapper;

    @Autowired
    private NameSpaceService nameSpaceService;

    @Autowired
    private ItemService itemService;

    public int save(String name, String intro, String appCode, String username) {
        TEnv env = new TEnv();
        env.setAppCode(appCode);
        env.setName(name);
        env.setIntro(intro);
        env.setCreateBy(username);
        env.setCreateTime(new Date());
        env.setUpdateBy(username);
        env.setUpdateTime(new Date());
        return envMapper.insertSelective(env);
    }

    public List<TEnv> selectEnvByNameAndAppcode(String name, String appCode) {
        TEnvExample example = new TEnvExample();
        example.createCriteria().andAppCodeEqualTo(appCode).andNameEqualTo(name);
        return envMapper.selectByExample(example);
    }

    public List<SimpleEnvVo> getEnvNameByAppcode(String appCode) {
        List<SimpleEnvVo> list = envMapper.getEnvNameByAppcode(appCode);
        return list;
    }

    public TEnv getEnvById(Integer envId) {
        TEnv env = envMapper.selectByPrimaryKey(envId);
        return env;
    }


    public EnvVo getEnvVOItem(Integer envId) {
        EnvVo env = envMapper.getEnvVOById(envId);
        return env;
    }

    public int deleteEnvById(Integer id) {
        return envMapper.deleteByPrimaryKey(id);
    }

    public List<TEnv> getEnvByAppcode(String appCode) {
        TEnvExample example = null;
        if(StringUtils.hasText(appCode)){
            example = new TEnvExample();
            example.createCriteria().andAppCodeEqualTo(appCode);
        }
        return envMapper.selectByExample(example);
    }

    public int updateEnv(Integer id, String name, String intro, String username) {
        TEnv env = new TEnv();
        env.setId(id);
        env.setName(name);
        env.setIntro(intro);
        env.setUpdateTime(new Date());
        env.setUpdateBy(username);
        return envMapper.updateByPrimaryKeySelective(env);
    }

    /**
     * 删除环境，已经环境下的所有关联内容
     *
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteEnvAndNsById(Integer id) {
        List<NameSpaceVo> nsList = nameSpaceService.getEnvNameSpaceByEnvId(id);
        for (NameSpaceVo ns : nsList) {
            itemService.deleteItemByNsId(ns.getId());
            nameSpaceService.deleteNsById(ns.getId());
        }
        return deleteEnvById(id);
    }

}

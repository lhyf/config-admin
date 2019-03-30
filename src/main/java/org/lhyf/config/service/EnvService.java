package org.lhyf.config.service;

import org.lhyf.config.mapper.EnvMapper;
import org.lhyf.config.model.Vo.EnvVo;
import org.lhyf.config.model.Vo.NameSpaceVo;
import org.lhyf.config.model.Vo.SimpleEnvVo;
import org.lhyf.config.pojo.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

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
    private EnvMapper envMapper;

    @Autowired
    private NameSpaceService nameSpaceService;

    @Autowired
    private ItemService itemService;

    public int save(String name, String intro, String appCode, String username) {
        Env env = new Env();
        env.setAppCode(appCode);
        env.setName(name);
        env.setIntro(intro);
        env.setCreateBy(username);
        env.setCreateTime(new Date());
        env.setUpdateBy(username);
        env.setUpdateTime(new Date());
        return envMapper.insertSelective(env);
    }

    public List<Env> selectEnvByNameAndAppcode(String name, String appCode) {
        Example example = new Example(Env.class);
        example.createCriteria().andEqualTo("appCode", appCode).andEqualTo("name", name);
        return envMapper.selectByExample(example);
    }

    public List<SimpleEnvVo> getEnvNameByAppcode(String appCode) {
        List<SimpleEnvVo> list = envMapper.getEnvNameByAppcode(appCode);
        return list;
    }

    public Env getEnvById(Integer envId) {
        Env env = envMapper.selectByPrimaryKey(envId);
        return env;
    }


    public EnvVo getEnvVOItem(Integer envId) {
        EnvVo env = envMapper.getEnvVOById(envId);
        return env;
    }

    public int deleteEnvById(Integer id) {
        return envMapper.deleteByPrimaryKey(id);
    }

    public List<Env> getEnvByAppcode(String appCode) {
        Example example = null;
        if (StringUtils.hasText(appCode)) {
            example = new Example(Env.class);
            example.createCriteria().andEqualTo("appCode", appCode);
        }
        return envMapper.selectByExample(example);
    }

    public int updateEnv(Integer id, String name, String intro, String username) {
        Env env = new Env();
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

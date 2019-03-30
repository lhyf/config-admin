package org.lhyf.config.service;

import org.lhyf.config.mapper.NameSpaceMapper;
import org.lhyf.config.model.Vo.NameSpaceVo;
import org.lhyf.config.pojo.Item;
import org.lhyf.config.pojo.NameSpace;
import org.lhyf.config.pojo.PubNameSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-14 17:04
 * @desc NameSpaceService
 *
 **/

@Service
public class NameSpaceService {

    @Autowired
    private NameSpaceMapper namespaceMapper;

    public int save(String code, String name, String intro, Integer envId, String username) {
        NameSpace namespace = new NameSpace();
        namespace.setSpaceCode(code);
        namespace.setName(name);
        namespace.setIntro(intro);
        namespace.setEnvId(envId);
        namespace.setCreateBy(username);
        namespace.setCreateTime(new Date());
        namespace.setUpdateBy(username);
        namespace.setUpdateTime(new Date());
        return namespaceMapper.insertSelective(namespace);
    }

    /**
     * 只查询Namespace,不会加载item
     *
     * @param envId
     * @return
     */
    public List<NameSpaceVo> getEnvNameSpaceByEnvId(Integer envId) {
        List<NameSpaceVo> list = namespaceMapper.selectNameSpaceByEnvId(envId);
        return list;
    }

    public int deleteNsById(Integer id) {
        return namespaceMapper.deleteByPrimaryKey(id);
    }

    public NameSpace getNamespaceById(Integer id) {
        NameSpace namespace = namespaceMapper.selectByPrimaryKey(id);
        return namespace;
    }

    public List<NameSpace> getNamespaceByEnvIdAndCode(Integer envId, String code) {
        Example example = new Example(NameSpace.class);
        example.createCriteria().andEqualTo("envId",envId).andEqualTo("spaceCode",code);
        List<NameSpace> list = namespaceMapper.selectByExample(example);
        return list;
    }

    public int updateNameSpace(Integer id, String code, String name, String intro, String username) {
        NameSpace ns = new NameSpace();
        ns.setId(id);
        ns.setSpaceCode(code);
        ns.setName(name);
        ns.setIntro(intro);
        ns.setUpdateBy(username);
        ns.setUpdateTime(new Date());
        return namespaceMapper.updateByPrimaryKeySelective(ns);
    }


    public int copyPubNsToPriNs(PubNameSpace pns, Integer envId, String username) {
        NameSpace ns = new NameSpace();
        ns.setSpaceCode(pns.getSpaceCode());
        ns.setName(pns.getName());
        ns.setIntro(pns.getIntro());
        ns.setUpdateBy(username);
        ns.setUpdateTime(new Date());
        ns.setCreateBy(username);
        ns.setCreateTime(new Date());
        ns.setEnvId(envId);
        namespaceMapper.insertSelective(ns);
        return ns.getId();
    }

    public List<NameSpace> getEnvNameSpaceByEnvIdAndCode(Integer envId, String code) {
        Example example = new Example(NameSpace.class);
        example.createCriteria().andEqualTo("envId",envId).andEqualTo("spaceCode",code);
        List<NameSpace> list = namespaceMapper.selectByExample(example);
        return list;
    }

    public List<NameSpace> getNameSpaceByIds(List<Integer> nsIdList) {
        return namespaceMapper.getNameSpaceByIds(nsIdList);
    }

    public int copyNamespace(NameSpace namespace, Integer envId, String username) {
        NameSpace ns = new NameSpace();
        ns.setSpaceCode(namespace.getSpaceCode());
        ns.setName(namespace.getName());
        ns.setIntro(namespace.getIntro());
        ns.setUpdateBy(username);
        ns.setUpdateTime(new Date());
        ns.setCreateBy(username);
        ns.setCreateTime(new Date());
        ns.setEnvId(envId);
        namespaceMapper.insertSelective(ns);
        return ns.getId();
    }
}

package org.lhyf.config.server.service;

import org.lhyf.config.server.mapper.TNamespaceMapper;
import org.lhyf.config.server.model.Vo.NameSpaceVo;
import org.lhyf.config.server.pojo.TNamespace;
import org.lhyf.config.server.pojo.TNamespaceExample;
import org.lhyf.config.server.pojo.TPubNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TNamespaceMapper namespaceMapper;

    public int save(String code, String name, String intro, Integer envId, String username) {
        TNamespace namespace = new TNamespace();
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

    public TNamespace getNamespaceById(Integer id) {
        TNamespace namespace = namespaceMapper.selectByPrimaryKey(id);
        return namespace;
    }

    public List<TNamespace> getNamespaceByEnvIdAndCode(Integer envId, String code) {
        TNamespaceExample example = new TNamespaceExample();
        example.createCriteria().andSpaceCodeEqualTo(code).andEnvIdEqualTo(envId);
        List<TNamespace> list = namespaceMapper.selectByExample(example);
        return list;
    }

    public int updateNameSpace(Integer id, String code, String name, String intro, String username) {
        TNamespace ns = new TNamespace();
        ns.setId(id);
        ns.setSpaceCode(code);
        ns.setName(name);
        ns.setIntro(intro);
        ns.setUpdateBy(username);
        ns.setUpdateTime(new Date());
        return namespaceMapper.updateByPrimaryKeySelective(ns);
    }


    public int copyPubNsToPriNs(TPubNamespace pns, Integer envId, String username) {
        TNamespace ns = new TNamespace();
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

    public List<TNamespace> getEnvNameSpaceByEnvIdAndCode(Integer envId, String code) {
        TNamespaceExample example = new TNamespaceExample();
        example.createCriteria().andEnvIdEqualTo(envId).andSpaceCodeEqualTo(code);
        List<TNamespace> list = namespaceMapper.selectByExample(example);
        return list;
    }

    public List<TNamespace> getNameSpaceByIds(List<Integer> nsIdList) {
        return namespaceMapper.getNameSpaceByIds(nsIdList);
    }

    public int copyNamespace(TNamespace namespace, Integer envId, String username) {
        TNamespace ns = new TNamespace();
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

package org.lhyf.config.server.service;

import org.lhyf.config.server.mapper.TPubNamespaceMapper;
import org.lhyf.config.server.model.Vo.PubNameSpaceVo;
import org.lhyf.config.server.pojo.TPubNamespace;
import org.lhyf.config.server.pojo.TPubNamespaceExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-12 17:27
 * @desc PubNamespaceService
 *
 **/
@Service
public class PubNamespaceService {

    @Autowired
    private TPubNamespaceMapper pubNamespaceMapper;


    /**
     * 查询所有的
     *
     * @return
     */
    public List<TPubNamespace> findAll() {
        List<TPubNamespace> namespaceList = pubNamespaceMapper.selectByExample(null);
        return namespaceList;
    }

    /**
     * 添加公共配置组
     *
     * @param code
     * @param name
     * @param intro
     * @param username 操作的用户名
     * @return
     */
    public int save(String code, String name, String intro, String username) {
        TPubNamespace namespace = new TPubNamespace();
        namespace.setName(name);
        namespace.setSpaceCode(code);
        namespace.setIntro(intro);
        namespace.setCreateBy(username);
        namespace.setCreateTime(new Date());

        return pubNamespaceMapper.insertSelective(namespace);
    }

    public List<PubNameSpaceVo> getPubNameSpaceWithItem() {
        return pubNamespaceMapper.getPubNameSpaceWithItem();
    }

    public int deleteNameSpaceById(Integer id) {
        return pubNamespaceMapper.deleteByPrimaryKey(id);
    }

    public TPubNamespace getPubNameSpaceById(Integer id) {
        TPubNamespace namespace = pubNamespaceMapper.selectByPrimaryKey(id);
        return namespace;
    }

    public int updatePubNamespace(Integer id, String code, String name, String intro, String username) {
        TPubNamespace namespace = new TPubNamespace();
        namespace.setId(id);
        namespace.setSpaceCode(code);
        namespace.setName(name);
        namespace.setIntro(intro);
        namespace.setUpdateBy(username);
        namespace.setUpdateTime(new Date());
        return pubNamespaceMapper.updateByPrimaryKeySelective(namespace);
    }

    public List<TPubNamespace> getPubNameSpaceByCode(String code) {
        TPubNamespaceExample example = new TPubNamespaceExample();
        example.createCriteria().andSpaceCodeEqualTo(code);
        List<TPubNamespace> namespaces = pubNamespaceMapper.selectByExample(example);
        return namespaces;
    }

    /**
     * 通过 id List集合获取namespace
     * @param nsIdList
     * @return
     */
    public List<TPubNamespace> getPubNameSpaceByIds(List<Integer> nsIdList) {
        return pubNamespaceMapper.getPubNameSpaceByIds(nsIdList);
    }
}

package org.lhyf.config.service;

import org.lhyf.config.mapper.PubNameSpaceMapper;
import org.lhyf.config.model.Vo.PubNameSpaceVo;
import org.lhyf.config.pojo.PubNameSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
    private PubNameSpaceMapper pubNamespaceMapper;


    /**
     * 查询所有的
     *
     * @return
     */
    public List<PubNameSpace> findAll() {
        List<PubNameSpace> namespaceList = pubNamespaceMapper.selectByExample(null);
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
        PubNameSpace namespace = new PubNameSpace();
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

    public PubNameSpace getPubNameSpaceById(Integer id) {
        PubNameSpace namespace = pubNamespaceMapper.selectByPrimaryKey(id);
        return namespace;
    }

    public int updatePubNamespace(Integer id, String code, String name, String intro, String username) {
        PubNameSpace namespace = new PubNameSpace();
        namespace.setId(id);
        namespace.setSpaceCode(code);
        namespace.setName(name);
        namespace.setIntro(intro);
        namespace.setUpdateBy(username);
        namespace.setUpdateTime(new Date());
        return pubNamespaceMapper.updateByPrimaryKeySelective(namespace);
    }

    public List<PubNameSpace> getPubNameSpaceByCode(String code) {
        Example example = new Example(PubNameSpace.class);
        example.createCriteria().andEqualTo("spaceCode",code);
        List<PubNameSpace> namespaces = pubNamespaceMapper.selectByExample(example);
        return namespaces;
    }

    /**
     * 通过 id List集合获取namespace
     * @param nsIdList
     * @return
     */
    public List<PubNameSpace> getPubNameSpaceByIds(List<Integer> nsIdList) {
        return pubNamespaceMapper.getPubNameSpaceByIds(nsIdList);
    }
}

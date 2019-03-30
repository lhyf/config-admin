package org.lhyf.config.service;

import org.lhyf.config.mapper.PubItemMapper;
import org.lhyf.config.pojo.NameSpace;
import org.lhyf.config.pojo.PubItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-12 18:32
 * @desc PubItemService
 *
 **/
@Service
public class PubItemService {

    @Autowired
    private PubItemMapper pubItemMapper;


    public List<PubItem> findIteamByNameSpaceId(Integer id) {
        Example example = new Example(PubItem.class);
        example.createCriteria().andEqualTo("pubNamespaceId",id);
        example.setOrderByClause("property");
        List<PubItem> items = pubItemMapper.selectByExample(example);

        return items;
    }

    public int save(String key, String value, String intro, Integer namespaceId, String username) {
        PubItem item = new PubItem();
        item.setProperty(key);
        item.setValue(value);
        item.setIntro(intro);
        item.setCreateBy(username);
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        item.setUpdateBy(username);
        item.setPubNamespaceId(namespaceId);
        return pubItemMapper.insertSelective(item);
    }

    public int update(Integer id, String key, String value, String intro, String username) {

        PubItem item = new PubItem();
        item.setId(id);
        item.setProperty(key);
        item.setValue(value);
        item.setIntro(intro);
        item.setUpdateBy(username);
        item.setUpdateTime(new Date());
        return pubItemMapper.updateByPrimaryKeySelective(item);
    }

    public int deletePubItem(Integer id) {
        int i = pubItemMapper.deleteByPrimaryKey(id);
        return i;
    }

    public List<PubItem> getPubItemsByIds(Integer[] pubItemId) {
        return pubItemMapper.getPubItemsByIds(pubItemId);
    }

    /**
     * 通过NamespaceId 删除item
     * @param nsId
     * @return
     */
    public int deletePubItemByNameSpaceId(Integer nsId) {
        Example example = new Example(PubItem.class);
        example.createCriteria().andEqualTo("pubNamespaceId",nsId);
        return pubItemMapper.deleteByExample(example);
    }

    public List<PubItem> getPubItemsByProperty(String property) {
        Example example = new Example(PubItem.class);
        example.createCriteria().andEqualTo("property",property);
        List<PubItem> items = pubItemMapper.selectByExample(example);
        return items;
    }

    public List<PubItem> selectItemByNSIdAndProperty(Integer namespaceId, String property) {
        Example example = new Example(PubItem.class);
        example.createCriteria().andEqualTo("property",property).andEqualTo("pubNamespaceId",namespaceId);
        List<PubItem> pubItems = pubItemMapper.selectByExample(example);
        return pubItems;
    }
}

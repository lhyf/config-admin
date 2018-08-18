package org.lhyf.config.server.service;

import org.lhyf.config.server.mapper.TPubItemMapper;
import org.lhyf.config.server.pojo.TPubItem;
import org.lhyf.config.server.pojo.TPubItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TPubItemMapper pubItemMapper;


    public List<TPubItem> findIteamByNameSpaceId(Integer id) {
        TPubItemExample example = new TPubItemExample();
        example.setOrderByClause("property");
        example.createCriteria().andPubNamespaceIdEqualTo(id);
        List<TPubItem> items = pubItemMapper.selectByExample(example);

        return items;
    }

    public int save(String key, String value, String intro, Integer namespaceId, String username) {
        TPubItem item = new TPubItem();
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

        TPubItem item = new TPubItem();
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

    public List<TPubItem> getPubItemsByIds(Integer[] pubItemId) {
        return pubItemMapper.getPubItemsByIds(pubItemId);
    }

    /**
     * 通过NamespaceId 删除item
     * @param nsId
     * @return
     */
    public int deletePubItemByNameSpaceId(Integer nsId) {
        TPubItemExample example = new TPubItemExample();
        example.createCriteria().andPubNamespaceIdEqualTo(nsId);
        return pubItemMapper.deleteByExample(example);
    }

    public List<TPubItem> getPubItemsByProperty(String property) {
        TPubItemExample example = new TPubItemExample();
        example.createCriteria().andPropertyEqualTo(property);
        List<TPubItem> items = pubItemMapper.selectByExample(example);
        return items;
    }

    public List<TPubItem> selectItemByNSIdAndProperty(Integer namespaceId, String property) {
        TPubItemExample example = new TPubItemExample();
        example.createCriteria().andPropertyEqualTo(property).andPubNamespaceIdEqualTo(namespaceId);
        List<TPubItem> pubItems = pubItemMapper.selectByExample(example);
        return pubItems;
    }
}

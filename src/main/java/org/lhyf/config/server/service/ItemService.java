package org.lhyf.config.server.service;

import org.lhyf.config.server.mapper.TItemMapper;
import org.lhyf.config.server.pojo.TItem;
import org.lhyf.config.server.pojo.TItemExample;
import org.lhyf.config.server.pojo.TPubItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-14 17:29
 * @desc ItemService
 *
 **/
@Service
public class ItemService {

    @Autowired
    private TItemMapper itemMapper;

    @Autowired
    private PubItemService pubItemService;

    public int save(String property, String value, String intro, Integer nsId, String username) {
        TItem item = new TItem();
        item.setProperty(property);
        item.setValue(value);
        item.setIntro(intro);
        item.setCreateBy(username);
        item.setCreateTime(new Date());
        item.setUpdateBy(username);
        item.setUpdateTime(new Date());
        item.setNamespaceId(nsId);
        return itemMapper.insertSelective(item);
    }

    public List<TItem> getAllItemByNameSpaceId(Integer nsId) {
        TItemExample example = new TItemExample();
        example.createCriteria().andNamespaceIdEqualTo(nsId);
        List<TItem> itemList = itemMapper.selectByExample(example);
        return itemList;
    }

    /**
     * 将TPubItem 拷贝到TItem
     *
     * @param pubI
     * @param nsId
     * @param username
     * @return
     */
    public int copyPubItemToPriItem(TPubItem pubI, Integer nsId, String username) {
        TItem item = new TItem();
        item.setProperty(pubI.getProperty());
        item.setValue(pubI.getValue());
        item.setIntro(pubI.getIntro());
        item.setCreateBy(username);
        item.setCreateTime(new Date());
        item.setUpdateBy(username);
        item.setUpdateTime(new Date());
        item.setNamespaceId(nsId);

        return itemMapper.insertSelective(item);
    }

    public int updateItem(Integer id, String property, String value, String intro, String username) {
        TItem item = new TItem();
        item.setId(id);
        item.setProperty(property);
        item.setValue(value);
        item.setIntro(intro);
        item.setUpdateBy(username);
        item.setUpdateTime(new Date());
        return itemMapper.updateByPrimaryKeySelective(item);
    }

    public List<TItem> selectItemByKey(String key) {
        TItemExample example = new TItemExample();
        example.createCriteria().andPropertyEqualTo(key);
        List<TItem> list = itemMapper.selectByExample(example);
        return list;
    }

    public int deleteItemById(Integer id) {
        return itemMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过namespace id 删除 item
     * @param id
     * @return
     */
    public int deleteItemByNsId(Integer id) {
        TItemExample example = new TItemExample();
        example.createCriteria().andNamespaceIdEqualTo(id);
        return itemMapper.deleteByExample(example);
    }

    /**
     * 将多个pubItem拷贝到 PriItem
     * @param newNsId priItem需要关联的namespace id
     * @param itemId pubItem Id集合
     * @param username
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int copyPubItemListToPriItem(int newNsId, List<Integer> itemId, String username) {
        Integer[] ids = new Integer[itemId.size()];
        itemId.toArray(ids);
        List<TPubItem> items = pubItemService.getPubItemsByIds(ids);

        TItem item = null;
        for(TPubItem pi:items){
            item = new TItem();
            item.setProperty(pi.getProperty());
            item.setValue(pi.getValue());
            item.setIntro(pi.getIntro());
            item.setNamespaceId(newNsId);
            item.setUpdateTime(new Date());
            item.setUpdateBy(username);
            item.setCreateTime(new Date());
            item.setCreateBy(username);
            itemMapper.insertSelective(item);
        }

        return 0;
    }

    /**
     * 客户端获取配置项
     * @param appcode
     * @param envname
     * @param nscode
     * @return
     */
    public List<TItem> selectItemByAppCodeAndEnvNameAndNScode(String appcode, String envname, String nscode) {
        return itemMapper.selectItemByAppCodeAndEnvNameAndNScode(appcode,envname,nscode);

    }

    public List<TItem> selectItemByAppCodeAndEnvName(String appcode, String envname) {
        return itemMapper.selectItemByAppCodeAndEnvName(appcode,envname);
    }

    /**
     * 查找一个环境下是否存在Key对应的配置项
     * @param envId
     * @param property
     * @return
     */
    public List<TItem> selectItemByEnvIdAndProperty(Integer envId, String property) {
        return itemMapper.selectItemByEnvIdAndProperty(envId,property);
    }


    public List<TItem> getItemByIds(Integer[] itemId) {
        return itemMapper.getItemByIds(itemId);
    }

    public int copyItem(TItem item, Integer nsId, String username) {
        TItem i = new TItem();
        i.setProperty(item.getProperty());
        i.setValue(item.getValue());
        i.setIntro(item.getIntro());
        i.setCreateBy(username);
        i.setCreateTime(new Date());
        i.setUpdateBy(username);
        i.setUpdateTime(new Date());
        i.setNamespaceId(nsId);
        return itemMapper.insertSelective(i);
    }

    public int copyItemByItemId(List<Integer> itemId, int newNsId, String username) {
        Integer[] ids = new Integer[itemId.size()];
        itemId.toArray(ids);
        List<TItem> items = getItemByIds(ids);

        TItem item = null;
        for(TItem pi:items){
            item = new TItem();
            item.setProperty(pi.getProperty());
            item.setValue(pi.getValue());
            item.setIntro(pi.getIntro());
            item.setNamespaceId(newNsId);
            item.setUpdateTime(new Date());
            item.setUpdateBy(username);
            item.setCreateTime(new Date());
            item.setCreateBy(username);
            itemMapper.insertSelective(item);
        }
        return 0;
    }
}

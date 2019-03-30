package org.lhyf.config.service;

import org.lhyf.config.mapper.ItemMapper;
import org.lhyf.config.pojo.Item;
import org.lhyf.config.pojo.PubItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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
    private ItemMapper itemMapper;

    @Autowired
    private PubItemService pubItemService;

    public int save(String property, String value, String intro, Integer nsId, String username) {
        Item item = new Item();
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

    public List<Item> getAllItemByNameSpaceId(Integer nsId) {
        Example example = new Example(Item.class);
        example.createCriteria().andEqualTo("namespaceId",nsId);
        List<Item> itemList = itemMapper.selectByExample(example);
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
    public int copyPubItemToPriItem(PubItem pubI, Integer nsId, String username) {
        Item item = new Item();
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
        Item item = new Item();
        item.setId(id);
        item.setProperty(property);
        item.setValue(value);
        item.setIntro(intro);
        item.setUpdateBy(username);
        item.setUpdateTime(new Date());
        return itemMapper.updateByPrimaryKeySelective(item);
    }

    public List<Item> selectItemByKey(String key) {
        Example example = new Example(Item.class);
        example.createCriteria().andEqualTo("property",key);
        List<Item> list = itemMapper.selectByExample(example);
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
        Example example = new Example(Item.class);
        example.createCriteria().andEqualTo("namespaceId",id);
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
        List<PubItem> items = pubItemService.getPubItemsByIds(ids);

        Item item = null;
        for(PubItem pi:items){
            item = new Item();
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
    public List<Item> selectItemByAppCodeAndEnvNameAndNScode(String appcode, String envname, String nscode) {
        return itemMapper.selectItemByAppCodeAndEnvNameAndNScode(appcode,envname,nscode);

    }

    public List<Item> selectItemByAppCodeAndEnvName(String appcode, String envname) {
        return itemMapper.selectItemByAppCodeAndEnvName(appcode,envname);
    }

    /**
     * 查找一个环境下是否存在Key对应的配置项
     * @param envId
     * @param property
     * @return
     */
    public List<Item> selectItemByEnvIdAndProperty(Integer envId, String property) {
        return itemMapper.selectItemByEnvIdAndProperty(envId,property);
    }


    public List<Item> getItemByIds(Integer[] itemId) {
        return itemMapper.getItemByIds(itemId);
    }

    public int copyItem(Item item, Integer nsId, String username) {
        Item i = new Item();
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
        List<Item> items = getItemByIds(ids);

        Item item = null;
        for(Item pi:items){
            item = new Item();
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

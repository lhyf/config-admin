package org.lhyf.config.mapper;

import org.apache.ibatis.annotations.Param;
import org.lhyf.config.model.Vo.ItemVo;
import org.lhyf.config.pojo.Item;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ItemMapper extends Mapper<Item> {
    List<ItemVo> selectItemVOByNameSpaceId(Integer namesapceId);

    List<Item> selectItemByAppCodeAndEnvNameAndNScode(@Param("appcode") String appcode, @Param("envname") String envname, @Param("nscode") String nscode);

    List<Item> selectItemByAppCodeAndEnvName(@Param("appcode") String appcode, @Param("envname") String envname);

    List<Item> selectItemByEnvIdAndProperty(@Param("envId") Integer envId, @Param("property") String property);

    List<Item> getItemByIds(Integer[] itemId);
}
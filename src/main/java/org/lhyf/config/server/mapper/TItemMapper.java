package org.lhyf.config.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.lhyf.config.server.model.Vo.ItemVo;
import org.lhyf.config.server.pojo.TItem;
import org.lhyf.config.server.pojo.TItemExample;

public interface TItemMapper {
    int countByExample(TItemExample example);

    int deleteByExample(TItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TItem record);

    int insertSelective(TItem record);

    List<TItem> selectByExample(TItemExample example);

    TItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TItem record, @Param("example") TItemExample example);

    int updateByExample(@Param("record") TItem record, @Param("example") TItemExample example);

    int updateByPrimaryKeySelective(TItem record);

    int updateByPrimaryKey(TItem record);

    List<ItemVo> selectItemVOByNameSpaceId(Integer namesapceId);

    List<TItem> selectItemByAppCodeAndEnvNameAndNScode(@Param("appcode") String appcode,@Param("envname") String envname,@Param("nscode") String nscode);

    List<TItem> selectItemByAppCodeAndEnvName(@Param("appcode") String appcode, @Param("envname") String envname);

    List<TItem> selectItemByEnvIdAndProperty(@Param("envId") Integer envId, @Param("property") String property);

    List<TItem> getItemByIds(Integer[] itemId);
}
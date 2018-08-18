package org.lhyf.config.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.lhyf.config.server.model.Vo.PubItemVo;
import org.lhyf.config.server.pojo.TPubItem;
import org.lhyf.config.server.pojo.TPubItemExample;

public interface TPubItemMapper {
    int countByExample(TPubItemExample example);

    int deleteByExample(TPubItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPubItem record);

    int insertSelective(TPubItem record);

    List<TPubItem> selectByExample(TPubItemExample example);

    TPubItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPubItem record, @Param("example") TPubItemExample example);

    int updateByExample(@Param("record") TPubItem record, @Param("example") TPubItemExample example);

    int updateByPrimaryKeySelective(TPubItem record);

    int updateByPrimaryKey(TPubItem record);

    List<TPubItem> getPubItemsByIds(Integer[] pubItemId);

    PubItemVo selectPubItemVoByPubNamespaceId(Integer spId);
}
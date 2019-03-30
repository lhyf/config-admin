package org.lhyf.config.mapper;

import org.lhyf.config.model.Vo.PubItemVo;
import org.lhyf.config.pojo.PubItem;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PubItemMapper extends Mapper<PubItem> {
    List<PubItem> getPubItemsByIds(Integer[] pubItemId);

    PubItemVo selectPubItemVoByPubNamespaceId(Integer spId);
}
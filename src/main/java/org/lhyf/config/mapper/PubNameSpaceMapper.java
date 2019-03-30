package org.lhyf.config.mapper;

import org.apache.ibatis.annotations.Param;
import org.lhyf.config.model.Vo.PubNameSpaceVo;
import org.lhyf.config.pojo.PubNameSpace;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PubNameSpaceMapper extends Mapper<PubNameSpace> {
    List<PubNameSpaceVo> getPubNameSpaceWithItem();

    List<PubNameSpace> getPubNameSpaceByIds(@Param("nsIdList") List<Integer> nsIdList);
}
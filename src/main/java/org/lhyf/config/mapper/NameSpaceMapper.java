package org.lhyf.config.mapper;

import org.lhyf.config.model.Vo.NameSpaceVo;
import org.lhyf.config.pojo.NameSpace;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface NameSpaceMapper extends Mapper<NameSpace> {
    /**查询namespace时加载item*/
    List<NameSpaceVo> selectNameSpaceVOByEnvId(Integer envId);

    /**只查询Namespace,不会加载item*/
    List<NameSpaceVo> selectNameSpaceByEnvId(Integer envId);

    List<NameSpace> getNameSpaceByIds(List<Integer> nsIdList);
}
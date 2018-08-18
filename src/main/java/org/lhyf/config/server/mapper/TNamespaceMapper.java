package org.lhyf.config.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.lhyf.config.server.model.Vo.NameSpaceVo;
import org.lhyf.config.server.pojo.TNamespace;
import org.lhyf.config.server.pojo.TNamespaceExample;

public interface TNamespaceMapper {
    int countByExample(TNamespaceExample example);

    int deleteByExample(TNamespaceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TNamespace record);

    int insertSelective(TNamespace record);

    List<TNamespace> selectByExample(TNamespaceExample example);

    TNamespace selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TNamespace record, @Param("example") TNamespaceExample example);

    int updateByExample(@Param("record") TNamespace record, @Param("example") TNamespaceExample example);

    int updateByPrimaryKeySelective(TNamespace record);

    int updateByPrimaryKey(TNamespace record);

    /**查询namespace时加载item*/
    List<NameSpaceVo> selectNameSpaceVOByEnvId(Integer envId);

    /**只查询Namespace,不会加载item*/
    List<NameSpaceVo> selectNameSpaceByEnvId(Integer envId);

    List<TNamespace> getNameSpaceByIds(List<Integer> nsIdList);
}
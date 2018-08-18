package org.lhyf.config.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.lhyf.config.server.model.Vo.PubNameSpaceVo;
import org.lhyf.config.server.pojo.TPubNamespace;
import org.lhyf.config.server.pojo.TPubNamespaceExample;

public interface TPubNamespaceMapper {
    int countByExample(TPubNamespaceExample example);

    int deleteByExample(TPubNamespaceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPubNamespace record);

    int insertSelective(TPubNamespace record);

    List<TPubNamespace> selectByExample(TPubNamespaceExample example);

    TPubNamespace selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPubNamespace record, @Param("example") TPubNamespaceExample example);

    int updateByExample(@Param("record") TPubNamespace record, @Param("example") TPubNamespaceExample example);

    int updateByPrimaryKeySelective(TPubNamespace record);

    int updateByPrimaryKey(TPubNamespace record);

    List<PubNameSpaceVo> getPubNameSpaceWithItem();

    List<TPubNamespace> getPubNameSpaceByIds(@Param("nsIdList") List<Integer> nsIdList);
}
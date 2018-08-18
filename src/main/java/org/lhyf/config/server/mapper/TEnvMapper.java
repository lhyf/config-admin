package org.lhyf.config.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.lhyf.config.server.model.Vo.EnvVo;
import org.lhyf.config.server.model.Vo.SimpleEnvVo;
import org.lhyf.config.server.pojo.TEnv;
import org.lhyf.config.server.pojo.TEnvExample;

public interface TEnvMapper {
    int countByExample(TEnvExample example);

    int deleteByExample(TEnvExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TEnv record);

    int insertSelective(TEnv record);

    List<TEnv> selectByExample(TEnvExample example);

    TEnv selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TEnv record, @Param("example") TEnvExample example);

    int updateByExample(@Param("record") TEnv record, @Param("example") TEnvExample example);

    int updateByPrimaryKeySelective(TEnv record);

    int updateByPrimaryKey(TEnv record);

    List<SimpleEnvVo> getEnvNameByAppcode(String appCode);

    EnvVo getEnvVOById(Integer envId);
}
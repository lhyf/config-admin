package org.lhyf.config.mapper;

import org.apache.ibatis.annotations.Param;
import org.lhyf.config.model.Vo.EnvVo;
import org.lhyf.config.model.Vo.SimpleEnvVo;
import org.lhyf.config.pojo.Env;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EnvMapper extends Mapper<Env> {

    List<SimpleEnvVo> getEnvNameByAppcode(String appCode);

    EnvVo getEnvVOById(@Param("envId") Integer envId);
}
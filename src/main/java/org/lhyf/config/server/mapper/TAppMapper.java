package org.lhyf.config.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.lhyf.config.server.pojo.TApp;
import org.lhyf.config.server.pojo.TAppExample;

public interface TAppMapper {
    int countByExample(TAppExample example);

    int deleteByExample(TAppExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TApp record);

    int insertSelective(TApp record);

    List<TApp> selectByExample(TAppExample example);

    TApp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TApp record, @Param("example") TAppExample example);

    int updateByExample(@Param("record") TApp record, @Param("example") TAppExample example);

    int updateByPrimaryKeySelective(TApp record);

    int updateByPrimaryKey(TApp record);
}
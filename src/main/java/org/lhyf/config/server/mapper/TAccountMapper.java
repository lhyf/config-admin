package org.lhyf.config.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.lhyf.config.server.pojo.TAccount;
import org.lhyf.config.server.pojo.TAccountExample;

public interface TAccountMapper {
    int countByExample(TAccountExample example);

    int deleteByExample(TAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAccount record);

    int insertSelective(TAccount record);

    List<TAccount> selectByExample(TAccountExample example);

    TAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAccount record, @Param("example") TAccountExample example);

    int updateByExample(@Param("record") TAccount record, @Param("example") TAccountExample example);

    int updateByPrimaryKeySelective(TAccount record);

    int updateByPrimaryKey(TAccount record);

    int updateAccountLoginTime(String username);

    TAccount getAccountByUserName(String username);
}
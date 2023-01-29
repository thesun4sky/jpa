package me.whitebear.jpa.mybatis.mapper;

import me.whitebear.jpa.mybatis.vo.AccountMyBatisVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapperV2 {

  @Select("SELECT * FROM account WHERE id = #{id}")
  AccountMyBatisVO selectAccount(Integer id);

  @Insert("INSERT INTO ACCOUNT (username, password) VALUES (#{username}, #{password})")
  void insertAccount(AccountMyBatisVO vo);
}

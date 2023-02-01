package me.whitebear.jpa.mybatis;

import me.whitebear.jpa.mybatis.configuration.DBConfiguration;
import me.whitebear.jpa.mybatis.mapper.AccountMapper;
import me.whitebear.jpa.mybatis.mapper.AccountMapperV2;
import me.whitebear.jpa.mybatis.vo.AccountMyBatisVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DBConfiguration.class)
@Disabled // 제한적인 TEST 환경으로 테스트 미수행
public class MyBatisTest {

  // Mapper 클래스를 받으려면 mapper.xml 빌드 해야하고, 그러려면 main 으로 옮겨서 해야함...
  @Autowired
  AccountMapper accountMapper;

  @Autowired
  AccountMapperV2 accountMapperV2;

  @Test
  @DisplayName("SQL Mapper - MyBatis 실습 (XML)")
  void sqlMapper_MyBatisTest() {
    // given

    // when
    accountMapper.insertAccount(new AccountMyBatisVO("new user3", "new password3"));
    var account = accountMapper.selectAccount(1);

    // then
    assert !account.getUsername().isEmpty();
  }

  @Test
  @DisplayName("SQL Mapper - MyBatis V2 실습 (Annotation)")
  void sqlMapper_MyBatisV2Test() {
    // given

    // when
    accountMapperV2.insertAccount(new AccountMyBatisVO("new user4", "new password4"));
    var account = accountMapperV2.selectAccount(1);

    // then
    assert !account.getUsername().isEmpty();
  }
}
package me.whitebear.jpa.mybatis.configuration;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
@MapperScan(basePackages = "me.whitebear.jpastudy.mapper.*")
@EnableTransactionManagement
public class DBConfiguration {

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    // mapping xml 파일 위치를 bean mapper location 에 등록
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    bean.setMapperLocations(resolver.getResources("classpath:mappings/*.xml"));
    return bean.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

}

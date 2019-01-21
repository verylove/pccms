//package pc.cms.com.mybatis;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * @author  weylan
// */
//@Configuration
//public class MybatisConfig {
//
//    @Autowired
//    DataSource dataSource;
//
//    @Bean
//    public SqlSessionFactory   sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml")
//        );
//        return bean.getObject();
//    }
//
//    @Bean
//    public DataSourceTransactionManager dataSourceTransactionManager(){
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
//        return  new SqlSessionTemplate(sqlSessionFactory);
//    }
//}

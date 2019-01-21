//package pc.cms.com.mybatis;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import tk.mybatis.spring.mapper.MapperScannerConfigurer;
//
//import java.util.Properties;
//
///**
// * @author  weylan
// * 该配置使得mybatis支持uuid主键生成策略,并支持回显
// */
//@Configuration
//public class TkMapperConfig {
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer(){
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setBasePackage("pc.cms.com.mapper");
//        Properties propertiesMapper = new Properties();
//        //通用mapper位置，不要和其他mapper、dao放在同一个目录
//        propertiesMapper.setProperty("mappers", "pc.cms.com.util.BaseMapper");
//        propertiesMapper.setProperty("notEmpty", "false");
//        //主键UUID回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER)
//        propertiesMapper.setProperty("ORDER","BEFORE");
//        mapperScannerConfigurer.setProperties(propertiesMapper);
//        return mapperScannerConfigurer;
//    }
//
//
//}

package pc.cms.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import pc.cms.com.converter.CustJwtAccessTokenConverter;
import pc.cms.com.handler.CustomAccessDeniedHandler;
import pc.cms.com.handler.CustomAuthEntryPoint;
import pc.cms.com.handler.CustomWebResponseExceptionTranslator;
import pc.cms.com.service.CustomUserDetailsService;
import pc.cms.com.service.impl.UsersServiceImpl;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @Author 彭铖
 * @Description //TODO
 * @Date 2:38 2018/12/16
 * @Param   配置资源服务器和授权服务器
 * @return
 **/
@Configuration
public class OAuth2ServerConfig {

    private static final String DEMO_RESOURCE_ID = "order";

    //资源服务器
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private CustomAuthEntryPoint customAuthEntryPoint;
        @Autowired
        private CustomAccessDeniedHandler customAccessDeniedHandler;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .exceptionHandling().authenticationEntryPoint(customAuthEntryPoint)
                    .and().authorizeRequests()
//                    .antMatchers("/oauth/remove_token").permitAll()
                    .antMatchers("/druid/**").permitAll()
                    .anyRequest().authenticated();
            ;
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            super.configure(resources);
            resources.authenticationEntryPoint(customAuthEntryPoint).accessDeniedHandler(customAccessDeniedHandler);
        }
    }

    //认证服务器
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private DataSource dataSource;

        @Autowired
        private CustomUserDetailsService customUserDetailsService;
        @Autowired
        private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;
        @Autowired
        private CustomAuthEntryPoint customAuthEntryPoint;
        @Autowired
        private CustomAccessDeniedHandler customAccessDeniedHandler;

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.allowFormAuthenticationForClients()
                    .checkTokenAccess("isAuthenticated()")
                    .tokenKeyAccess("permitAll()")
                    .authenticationEntryPoint(customAuthEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler);
//            log.info("AuthorizationServerSecurityConfigurer is complete");
        }

        /**
         * 配置客户端详情信息(Client Details)
         * clientId：（必须的）用来标识客户的Id。
         * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
         * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
         * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
         * authorities：此客户端可以使用的权限（基于Spring Security authorities）。
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetails());
//            log.info("ClientDetailsServiceConfigurer is complete!");
        }

        /**
         * 配置授权、令牌的访问端点和令牌服务
         * tokenStore：采用redis储存
         * authenticationManager:身份认证管理器, 用于"password"授权模式
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .authenticationManager(authenticationManager)
                    .userDetailsService(customUserDetailsService)
                    .tokenServices(tokenServices())
                    .exceptionTranslator(customWebResponseExceptionTranslator);

//            log.info("AuthorizationServerEndpointsConfigurer is complete.");
        }


        /**
         * redis存储方式
         *
         * @return
         */
        @Bean("redisTokenStore")
        public TokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

   /* @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }*/


        /**
         * 客户端信息配置在数据库
         *
         * @return
         */
        @Bean
        public ClientDetailsService clientDetails() {
            return new JdbcClientDetailsService(dataSource);
        }

        /**
         * 采用RSA加密生成jwt
         *
         * @return
         */
//        @Bean
//        public JwtAccessTokenConverter jwtTokenEnhancer() {
//            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("pccms.jks"), "mypass".toCharArray());
//       /* JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("hq-jwt"));*/
//            CustJwtAccessTokenConverter tokenConverter = new CustJwtAccessTokenConverter();
//            tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
//            return tokenConverter;
//        }
        /**
         *@作   者:     pc
         *@修改时间:     2019/1/8 17:40
         *@方法描述:    对称加密
         *@参   数:
         *@返回 值:
         */
//        @Bean
//        public JwtAccessTokenConverter jwtTokenEnhancer() {
//            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//            converter.setSigningKey("pccms");
//            return converter;
//        }

        /**
         * 配置生成token的有效期以及存储方式（此处用的redis）
         *
         * @return
         */
        @Bean
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(redisTokenStore());
//            defaultTokenServices.setTokenEnhancer(jwtTokenEnhancer());
            defaultTokenServices.setSupportRefreshToken(true);
            defaultTokenServices.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(2));
            defaultTokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
            return defaultTokenServices;
        }
    }

}

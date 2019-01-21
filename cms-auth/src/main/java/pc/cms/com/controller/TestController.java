package pc.cms.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import pc.cms.com.entity.Result;
import pc.cms.com.enums.ResultEnum;

import java.security.Principal;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    @Qualifier("redisTokenStore")
    private TokenStore tokenStore;

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/oauth/remove_token")
    public Result removeToken(@RequestParam("token") String token) {

        if (token != null) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            tokenStore.removeAccessToken(accessToken);
        } else {
            return new Result(ResultEnum.TOKEN_MISS);
        }

        return Result.returnOk();
    }
    @RequestMapping("/test")
    public Result test(){

        return Result.returnOk();
    }


}

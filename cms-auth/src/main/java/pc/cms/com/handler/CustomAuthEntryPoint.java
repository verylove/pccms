package pc.cms.com.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import pc.cms.com.entity.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hq
 * @Package com.hq.cloud.oauth2server.handler
 * @Description: UnauthorizedHandler
 * @date 2018/4/19 10:22
 */
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    /**
     * 自定义EntryPoint用于tokan校验失败返回信息
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json形式的错误信息
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

//        Result result = new Result(String.valueOf(HttpServletResponse.SC_BAD_REQUEST), "token校验失败");

        Map map=new HashMap();
        map.put("error",String.valueOf(HttpServletResponse.SC_BAD_REQUEST));
        map.put("message","token校验失败");
        map.put("path",httpServletRequest.getServletPath());
        map.put("timestamp", String.valueOf(new Date().getTime()));

        httpServletResponse.getWriter().print(JSON.toJSONString(map));  //result.toString()
        httpServletResponse.getWriter().flush();

    }
}

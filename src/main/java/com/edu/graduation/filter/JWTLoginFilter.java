package com.edu.graduation.filter;

import com.edu.graduation.entity.dto.LoginDTO;
import com.edu.graduation.entity.dto.TokenDTO;
import com.edu.graduation.enums.BackMessageEnum;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，并生成token。
 * @author q
 */
@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 私钥
     */
    private String KEY = "spring-security-@Jwt!&Secret^#";
    /**
     * token的开头
     */
    private String tokenHead = "Bearer ";
    /**
     * token头部
     */
    private String tokenHeader = "Authorization";
    /**
     * token的有效期  12小时   毫秒单位
     */
    private long expiration = 12*60*60*1000;
    /**
     * 接收并解析用户凭证,从request中取出authentication
     */
    public Authentication attemptAuthentication(LoginDTO loginDTO) throws AuthenticationException {
        Authentication authenticationToken = null;
        /* 登录是否有id和密码 */
        if(StringUtils.isEmpty(loginDTO.getUserId())&& StringUtils.isEmpty(loginDTO.getPassword())) {
            authenticationToken = new UsernamePasswordAuthenticationToken("", "");
        }else{
            authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserId(),loginDTO.getPassword());
        }
        return authenticationToken;
    }

    /**
     * 生成token
     * token：
     * header(头)：alg(指明算法) typ（token类型 JWT  此处不必写）
     * payload(负载)：
     * 系统保留的声明（建议使用）：iss（签发者） exp（过期时间）  sub（主题）
     * 公共声明：
     * 私有声明：根据业务需求定义
     * signature(签名)：base64编码的header、base64编码的payload和秘钥（secret）进行运算
     * @param response
     * @param authResult
     */
    /**
     * 用户成功登录后，该方法被调用
     */
    public ResultVo successfulAuthentication(Authentication authResult, HttpServletResponse response, String userName) {

        Map<String,Object> claims = new HashMap<>(16);
        TokenDTO tokenDTO = new TokenDTO();
        Date expireTime = new Date(System.currentTimeMillis()+expiration);
        claims.put("name",authResult.getPrincipal());
        claims.put("time",System.currentTimeMillis());
        claims.put("role",authResult.getAuthorities());
        log.info("role: "+authResult.getAuthorities());

        //生成token
        String token = Jwts.builder()
                .setClaims(claims)
                //设置过期时间
                .setExpiration(expireTime)
                //签名
                .signWith(SignatureAlgorithm.HS256,KEY)
                .compact();

        String responseToken = tokenHead + token;
        tokenDTO.setAuthorization(responseToken);
        tokenDTO.setUserName(userName);
        log.info("token过期时间={}",expireTime);

        //返回token
        response.addHeader(tokenHeader,responseToken);
        return ResultVoUtil.success(BackMessageEnum.LOGIN_SUCCESS.getMessage(),tokenDTO);
    }

}

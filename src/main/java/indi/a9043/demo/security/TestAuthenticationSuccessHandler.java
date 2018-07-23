package indi.a9043.demo.security;

import indi.a9043.demo.entity.TestUser;
import indi.a9043.demo.util.JwtUtil;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        TestUser testUser = (TestUser) authentication.getPrincipal();
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userName", testUser.getUserName());

        String token = JwtUtil.createJWT(claimsMap);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access-token", token);
        response.getWriter().write(jsonObject.toString());
    }
}

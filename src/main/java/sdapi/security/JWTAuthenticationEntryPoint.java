package sdapi.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        if (authException.getClass().equals(InsufficientAuthenticationException.class)) {
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().append(json_unauthorized());
        } else {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json_unauthentic());
        }
    }

    private String json_unauthorized() {
        long date = new Date().getTime();
        return "{\"timestamp\": " + date + ", "
                + "\"status\": 403, "
                + "\"error\": \"Forbidden\", "
                + "\"message\": \"Você não tem permissão para executar esta ação.\", "
                + "\"path\": \"/login\"}";
    }

    private String json_unauthentic() {
        long date = new Date().getTime();
        return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Unauthorized\", "
                + "\"message\": \"Email e/ou senha inválido.\", "
                + "\"path\": \"/login\"}";
    }
}
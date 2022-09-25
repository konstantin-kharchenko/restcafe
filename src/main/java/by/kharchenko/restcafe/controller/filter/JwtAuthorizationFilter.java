package by.kharchenko.restcafe.controller.filter;

import by.kharchenko.restcafe.model.entity.JwtType;
import by.kharchenko.restcafe.security.JwtAuthentication;
import by.kharchenko.restcafe.security.JwtTokenProvider;
import by.kharchenko.restcafe.util.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String ACCESS_TOKEN = "Access-Token";
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = request.getHeader(ACCESS_TOKEN);
        if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken, JwtType.ACCESS)) {
                try {
                    JwtAuthentication auth = JwtUtils.generate(jwtTokenProvider.getAccessClaims(accessToken));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } catch (Exception ex) {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }
        chain.doFilter(request, response);
    }
}

package by.kharchenko.restcafe.config;

import by.kharchenko.restcafe.controller.filter.JwtAuthorizationFilter;
import by.kharchenko.restcafe.model.entity.RoleType;
import by.kharchenko.restcafe.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable().authorizeRequests()
                .antMatchers("/login", "/registration").anonymous()
                .antMatchers("/client/**", "/admin/**").authenticated()
                .antMatchers(HttpMethod.GET, "/admin/**").hasAuthority(RoleType.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.GET, "/client/**").hasRole(RoleType.CLIENT.name())
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider));
        /*http.logout()
                .logoutUrl("/api/v1/users/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .clearAuthentication(true)
                .invalidateHttpSession(true);*/
    }
}


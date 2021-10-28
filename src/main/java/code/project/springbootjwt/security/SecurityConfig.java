package code.project.springbootjwt.security;

import code.project.springbootjwt.security.filters.CookieAuthorizationFilter;
import code.project.springbootjwt.security.filters.JWTAuthorizationFilter;
import code.project.springbootjwt.security.filters.Auth0AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.RememberMeServices;

import static code.project.springbootjwt.security.SecurityConstants.TASKS_URL;

public class SecurityConfig {

    @Configuration
    public static class JWTConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .cors().disable()
                    .csrf().disable();

            http.authorizeRequests()
                        .antMatchers(HttpMethod.POST, TASKS_URL).permitAll() // permit all access to these endpoints.
                        .anyRequest().authenticated(); //any other requests needs to be authenticated

            http.antMatcher(TASKS_URL)
                    .addFilter(new CookieAuthorizationFilter(getAuthenticationManager(), getRememberMe()))
                    .addFilterAfter(new JWTAuthorizationFilter(getAuthenticationManager(), TASKS_URL), CookieAuthorizationFilter.class)
                    .addFilterAfter(new Auth0AuthorizationFilter(TASKS_URL), JWTAuthorizationFilter.class)
                    .rememberMe().alwaysRemember(true);
        }

        @Bean
        public AuthenticationManager getAuthenticationManager() {
            return new JWTAuthenticationManager();
        }

        @Bean
        public RememberMeServices getRememberMe() {
            return new CookieBasedRememberMe();
        }
    }
}

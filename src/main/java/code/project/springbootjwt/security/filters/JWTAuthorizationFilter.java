package code.project.springbootjwt.security.filters;

import code.project.springbootjwt.security.BearerToken;
import code.project.springbootjwt.security.SecurityConstants;
import code.project.springbootjwt.security.util.CookieUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static code.project.springbootjwt.security.SecurityConstants.TOKEN;

public class JWTAuthorizationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticateManager;

    public JWTAuthorizationFilter(AuthenticationManager authManager, String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SecurityConstants.TASKS_URL, "GET"));
        this.authenticateManager = authManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        BearerToken bearerToken = (BearerToken) SecurityContextHolder.getContext().getAuthentication();
        if (bearerToken.isCookieAuthenticated()) {
            return bearerToken;
        } else {
            String token = request.getParameter(TOKEN);
            if (token == null) {
                throw new RememberMeAuthenticationException("No token");
            }
            return authenticateManager.authenticate(new BearerToken(token));
        }

    }

    @Override
    protected final void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.addCookie(CookieUtil.createHTTPOnlyCookie(HttpHeaders.AUTHORIZATION, request.getParameter(TOKEN)));
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        BearerToken bearerToken = (BearerToken) SecurityContextHolder.getContext().getAuthentication();

        if (!requiresAuthentication(request, response) || bearerToken.isCookieAuthenticated()) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authResult;
        try {
            authResult = attemptAuthentication(request, response);
            if (authResult == null) {
                return;
            } else {
                successfulAuthentication(request, response, chain, authResult);
            }
        } catch (AuthenticationException failed) {
            chain.doFilter(request, response);
            return;
        }
    }
}
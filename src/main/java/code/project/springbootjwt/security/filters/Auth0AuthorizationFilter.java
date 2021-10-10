package code.project.springbootjwt.security.filters;

import code.project.springbootjwt.security.SecurityConstants;
import code.project.springbootjwt.security.util.URLUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static code.project.springbootjwt.security.SecurityConstants.OAUTH0_AUTHORIZE_ENDPOINT;
import static code.project.springbootjwt.security.SecurityConstants.OAUTH0_CLIENT;
import static code.project.springbootjwt.security.SecurityConstants.TASKS_URL;

public class Auth0AuthorizationFilter extends AbstractAuthenticationProcessingFilter {

    public Auth0AuthorizationFilter(String defaultFilterProcessesUrl) {

    	super(defaultFilterProcessesUrl);
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SecurityConstants.TASKS_URL, "GET"));
    }

	@Override public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (requiresAuthentication(request, response) && !authentication.isAuthenticated()) {
			String redirectUri = URLUtil.getBaseUrl(request);
			String callbackRedirectUri = redirectUri + "/callback?redirect_uri=" + TASKS_URL;
			response.sendRedirect(
					OAUTH0_AUTHORIZE_ENDPOINT
							+ "?redirect_uri=" + callbackRedirectUri +
							"&client_id=" + OAUTH0_CLIENT +
							"&scope=openid%20profile%20email" +
							"&response_type=code" +
							"&state=b3jMmuWV80R5-AZA02H5r-k4SS5sN5ZEpkHb72aL7hM" +
							"&audience=" + redirectUri + TASKS_URL);
		} else {
			chain.doFilter(request, response);
		}
	}



	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		return null;
	}

}
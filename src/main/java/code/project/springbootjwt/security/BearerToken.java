package code.project.springbootjwt.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class BearerToken extends AbstractAuthenticationToken{

	private static final long serialVersionUID = 1L;
	private final Object bearer;
	private boolean isCookieAuthenticated = false;

	public BearerToken() {
		super(null);
		this.bearer = null;
		setAuthenticated(false);
	}
	
	public BearerToken(Object token) {
		super(null);
		this.bearer = token;
	}

	@Override
	public Object getCredentials() {
		return bearer;
	}

	@Override
	public Object getPrincipal() {
		return bearer;
	}

	public boolean isCookieAuthenticated() {
		return isCookieAuthenticated;
	}

	public void setCookieAuthenticated(boolean cookieAuthenticated) {
		isCookieAuthenticated = cookieAuthenticated;
	}
}

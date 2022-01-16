package code.project.springbootjwt.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class BearerToken extends AbstractAuthenticationToken{

	private static final long serialVersionUID = 1L;
	private final String  token;
	private boolean isPresentInCookie = false;

	public BearerToken() {
		super(Collections.emptyList());
		this.token = null;
		setAuthenticated(false);
	}
	
	public BearerToken(String token) {
		super(Collections.emptyList());
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	public boolean isTokenPresent(){
		return token != null;
	}

	public boolean isPresentInCookie() {
		return isPresentInCookie;
	}


	public void setPresentInCookie(boolean presentInCookie) {
		isPresentInCookie = presentInCookie;
	}
}

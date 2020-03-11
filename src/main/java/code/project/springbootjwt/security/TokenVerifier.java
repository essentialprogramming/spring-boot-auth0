package code.project.springbootjwt.security;

import code.project.springbootjwt.security.util.TokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.function.Function;

public class TokenVerifier implements AuthenticationManager {

	private Function<Object, Boolean> isTokenValid = (token -> TokenUtil.parse(String.valueOf(token))
			.map(claims -> true) // for now just check if was parsed succesfuly
			.orElse(false));

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		authentication.setAuthenticated(isTokenValid.apply(authentication.getPrincipal()));
		return authentication;
	}
}

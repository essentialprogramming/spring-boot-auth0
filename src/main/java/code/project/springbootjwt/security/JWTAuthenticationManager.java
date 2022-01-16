package code.project.springbootjwt.security;

import code.project.springbootjwt.security.jwt.JwtUtil;
import code.project.springbootjwt.security.jwt.ValidationResponse;
import code.project.springbootjwt.security.util.PemUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class JWTAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			BearerToken bearerToken = (BearerToken) authentication;
			ValidationResponse validationResponse = JwtUtil.verifyJwt(bearerToken.getToken(), PemUtils.getPublicKeyFromPEM());
			authentication.setAuthenticated(validationResponse.isValid());
		} catch (Exception e) {
			authentication.setAuthenticated(false);
		}

		return authentication;
	}
}

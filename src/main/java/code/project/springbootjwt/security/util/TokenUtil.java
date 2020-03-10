package code.project.springbootjwt.security.util;

import code.project.springbootjwt.security.jwt.JwtClaims;
import code.project.springbootjwt.security.jwt.JwtUtil;
import code.project.springbootjwt.security.jwt.ValidationResponse;

import java.util.Optional;

public class TokenUtil {

	public static Optional<JwtClaims> parse(String token) {
		if (token == null) {
			return Optional.empty();
		}
		try {
			ValidationResponse validationResponse = JwtUtil.verifyJwt(token, PemUtils.getPublicKeyFromPEM());
			return Optional.ofNullable(validationResponse.getClaims());
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}

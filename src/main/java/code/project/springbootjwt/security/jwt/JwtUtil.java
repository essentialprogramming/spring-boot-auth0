package code.project.springbootjwt.security.jwt;


import code.project.springbootjwt.security.jwt.crypto.HMACProvider;
import code.project.springbootjwt.security.jwt.crypto.RSAProvider;
import code.project.springbootjwt.security.jwt.crypto.SignatureProvider;
import code.project.springbootjwt.security.jwt.exception.SignatureValidationException;
import code.project.springbootjwt.security.jwt.exception.TokenValidationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {

    private static final SignatureProvider provider = (jwt, key) -> {
        try {
            switch (jwt.getHeader().getAlgorithm().getSignatureType()) {
                case "RSA":
                    return RSAProvider.getInstance().verify(jwt, key);
                case "HMAC":
                    return HMACProvider.getInstance().verify(jwt, key);
                default:
                    return true;
            }
        } catch (IOException e) {
            throw new SignatureValidationException();
        }

    };
    private JwtUtil() {
    }

    /**
     * Get the Header as defined in the 6.1 section of the JWT specification
     * (http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1)
     *
     * @param base64jsonString base64 encoded Token
     * @return the decoded JWT header
     */
    public static String getHeader(String base64jsonString) {
        return decodeJSON(base64jsonString.split("\\.")[0]);
    }

    /**
     * Get the Claims Set as defined in the 6.1 section of the JWT specification
     * (http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1)
     *
     * @param base64jsonString base64 encoded Token
     * @return the decoded JWT claim set
     */
    public static String getClaimsSet(String base64jsonString) {
        return decodeJSON(base64jsonString.split("\\.")[1]);
    }

    private static String decodeJSON(String base64jsonString) {
        return new String(Base64.getUrlDecoder().decode(base64jsonString), StandardCharsets.UTF_8);
    }

    /**
     * Verify JWT token format, signature and expiration time.
     *
     * @param jwt The JWT token Base64 encoded
     * @param key The key used to check signature
     * @return ValidationResponse
     * @throws TokenValidationException If the JWT token has invalid format or is null, the key is
     *                                  null, the signature can't be validated.
     */
    public static ValidationResponse verifyJwt(String jwt, Key key) throws TokenValidationException {
        JwtClaims claims;
        boolean isSignatureValid;
        boolean isClaimValid = false;

        if (key == null) {
            throw new TokenValidationException("Key must not be null");
        }
        Jwt jwtToken = new Jwt(jwt);

        try {
            isSignatureValid = provider.verify(jwtToken, key);

            claims = jwtToken.getClaims();
            Date current = new Date();
            if (current.getTime() / 1000 < claims.getExpiration()) {
                isClaimValid = true;
            }

        } catch (IOException e) {
            throw new TokenValidationException("Token validation failed");
        }

        return new ValidationResponse(isSignatureValid  && isClaimValid , claims);
    }

    /**
     * Get key id from JWT token. This id will be used to identity the right Public Key to verify the
     * signature.
     *
     * @param jwt The JWT token Base64 encoded
     * @return key id which can be use to validate signature
     * @throws TokenValidationException If the JWT token has invalid format or is null
     */
    public static String getKeyId(String jwt) throws TokenValidationException {
        try {
            Jwt jwtToken = new Jwt(jwt);
            JwtHeader header = jwtToken.getHeader();
            return header.getKeyId();
        } catch (IOException e) {
            throw new TokenValidationException("Error parsing JWT");
        }
    }
}

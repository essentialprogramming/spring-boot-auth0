package code.project.springbootjwt.security.jwt;

import code.project.springbootjwt.security.jwt.crypto.HMACProvider;
import code.project.springbootjwt.security.jwt.crypto.RSAProvider;
import code.project.springbootjwt.security.jwt.exception.TokenValidationException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Pattern;

public class JwtUtil {

    private JwtUtil() {
    }

    /**
     * Get the Header as defined in the 6.1 section of the JWT specification
     * (http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1)
     *
     * @param base64jsonString
     * @return the decoded JWT header
     */
    public static String getHeader(String base64jsonString) {
        return decodeJSON(base64jsonString.split("\\.")[0]);
    }

    /**
     * Get the Claims Set as defined in the 6.1 section of the JWT specification
     * (http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1)
     *
     * @param base64jsonString
     * @return the decoded JWT claim set
     */
    public static String getClaimsSet(String base64jsonString) {
        return decodeJSON(base64jsonString.split("\\.")[1]);
    }

    private static String decodeJSON(String base64jsonString) {
        return new String(Base64.getUrlDecoder().decode(base64jsonString), Charset.forName("UTF-8"));
    }

    /**
     * Parse the jwt token from Authorization header.
     *
     * @param authorization authorization header.
     * @return JWT token
     */
    public static String getJwtFromAuthorization(String authorization) {
        String jwt = null;
        if (authorization != null) {
            String[] parts = authorization.split(" ");
            if (parts.length == 2) {
                String scheme = parts[0];
                String credentials = parts[1];
                Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
                if (pattern.matcher(scheme).matches()) {
                    jwt = credentials;
                }
            }
        }
        return jwt;
    }

    /**
     * Verify JWT token format, signature and expiration time.
     *
     * @param jwt The JWT token Base64 encoded
     * @param key The key used to check signature
     * @return
     * @throws TokenValidationException If the JWT token has invalid format or is null, the key is
     *                                  null, the signature can't be validated.
     */
    public static ValidationResponse verifyJwt(String jwt, Key key) throws TokenValidationException {
        JwtClaims claims;
        boolean isSignatureValid = false;
        boolean isClaimValid = false;

        if (key == null) {
            throw new TokenValidationException("Key must not be null");
        }
        Jwt jwtToken = new Jwt(jwt);

        try {
            JwtHeader jwtHeader = jwtToken.getHeader();
            claims = jwtToken.getClaims();

            if (jwtHeader.getAlgorithm().isRsa()) {
                RSAProvider rsaProvider = RSAProvider.getInstance();
                isSignatureValid = rsaProvider.verify(jwtToken, key);
            }
            if (jwtHeader.getAlgorithm().isHmac()) {
                HMACProvider hmacProvider = HMACProvider.getInstance();
                isSignatureValid = hmacProvider.verify(jwtToken, key);
            }

            Date current = new Date();
            if (current.getTime() / 1000 < claims.getExpiration()) {
                isClaimValid = true;
            }

        } catch (IOException e) {
            throw new TokenValidationException("Token validation failed");
        }

        return new ValidationResponse(isClaimValid && isSignatureValid, claims);
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

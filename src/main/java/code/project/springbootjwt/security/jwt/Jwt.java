package code.project.springbootjwt.security.jwt;

import code.project.springbootjwt.security.jwt.exception.TokenValidationException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;

/**
 * JSON Web Token.
 */
public class Jwt {

    private final String base64Header;
    private final String base64Content;
    private final String base64Signature;

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    public Jwt(final String input) throws TokenValidationException {
        if (input == null) {
            throw new TokenValidationException("JWT must not be null");
        }
        final String[] parts = input.split("\\.");
        if (parts.length < 2 || parts.length > 3)
            throw new TokenValidationException("Parsing error");
        base64Header = parts[0];
        base64Content = parts[1];
        base64Signature = isDigitallySigned(input) ? parts[2] : null;
    }

    public String getEncodedHeader() {
        return base64Header;
    }

    public String getEncodedContent() {
        return base64Content;
    }

    public String getEncodedSignature() {
        return base64Signature;
    }

    public JwtHeader getHeader() throws IOException {
        byte[] headerBytes = Base64.getUrlDecoder().decode(base64Header);
        return mapper.readValue(headerBytes, JwtHeader.class);
    }

    public byte[] getContent() {
        return Base64.getUrlDecoder().decode(base64Content);
    }

    public byte[] getSignature() {
        return Base64.getUrlDecoder().decode(base64Signature);
    }

    private boolean isDigitallySigned(String jwt) {
        String[] parts = jwt.split("\\.");
        return parts.length > 2;
    }

    public JwtClaims getClaims() throws IOException {
        byte[] claimsBytes = Base64.getUrlDecoder().decode(base64Content);
        return mapper.readValue(claimsBytes, JwtClaims.class);
    }
}

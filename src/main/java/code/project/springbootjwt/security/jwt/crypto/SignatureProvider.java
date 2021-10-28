package code.project.springbootjwt.security.jwt.crypto;

import code.project.springbootjwt.security.jwt.Jwt;
import code.project.springbootjwt.security.jwt.exception.TokenValidationException;

import java.security.Key;

public interface SignatureProvider {
    boolean verify(Jwt input, Key key) throws TokenValidationException;
}

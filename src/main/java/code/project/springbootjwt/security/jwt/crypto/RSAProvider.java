package code.project.springbootjwt.security.jwt.crypto;

import code.project.springbootjwt.security.jwt.Jwt;
import code.project.springbootjwt.security.jwt.SignatureAlgorithm;
import code.project.springbootjwt.security.jwt.exception.TokenValidationException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;

public class RSAProvider implements SignatureProvider {

    private RSAProvider() {
    }

    private static class RSAProviderHolder {
        static final RSAProvider INSTANCE = new RSAProvider();
    }

    public static RSAProvider getInstance() {
        return RSAProviderHolder.INSTANCE;
    }

    private static String getJavaAlgorithm(SignatureAlgorithm alg) {
        switch (alg) {
            case RS256:
                return "SHA256withRSA";
            case RS384:
                return "SHA384withRSA";
            case RS512:
                return "SHA512withRSA";
            default:
                throw new IllegalArgumentException("Not an RSA Algorithm");
        }
    }

    private static Signature getSignature(SignatureAlgorithm alg) throws NoSuchAlgorithmException {
        return Signature.getInstance(getJavaAlgorithm(alg));
    }

    private static boolean verify(Jwt input, PublicKey publicKey) throws TokenValidationException {
        try {
            Signature verifier = getSignature(input.getHeader().getAlgorithm());
            verifier.initVerify(publicKey);
            verifier
                    .update((input.getEncodedHeader() + '.' + input.getEncodedContent()).getBytes("UTF-8"));
            return verifier.verify(input.getSignature());
        } catch (Exception e) {
            throw new TokenValidationException("Something went wrong on signature validation");
        }
    }

    @Override
    public boolean verify(Jwt input, Key key) throws TokenValidationException {
        return verify(input, (PublicKey) key);
    }

}

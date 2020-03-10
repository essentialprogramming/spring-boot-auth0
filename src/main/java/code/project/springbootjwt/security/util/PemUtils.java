package code.project.springbootjwt.security.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class PemUtils {

    public static PublicKey getPublicKeyFromPEM() {
        try {
            File file = new File("src/main/resources/oauth0_key.pem");
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            FileInputStream is = new FileInputStream (file);
            X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
            return cer.getPublicKey();
        } catch (Exception e) {
            return null;
        }
    }
}
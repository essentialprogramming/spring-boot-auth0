package code.project.springbootjwt.security.jwt;

public enum SignatureAlgorithm {

    /**
     * No digital signature or MAC performed
     */
    NONE("none", "No digital signature or MAC performed", "None", null),

    /**
     * HMAC using SHA-256
     */
    HS256("HS256", "HMAC using SHA-256", "HMAC", "HmacSHA256"),

    /**
     * HMAC using SHA-384
     */
    HS384("HS384", "HMAC using SHA-384", "HMAC", "HmacSHA384"),

    /**
     * HMAC using SHA-512
     */
    HS512("HS512", "HMAC using SHA-512", "HMAC", "HmacSHA512"),

    /**
     * RSASSA-PKCS-v1_5 using SHA-256
     */
    RS256("RS256", "RSASSA-PKCS-v1_5 using SHA-256", "RSA", "SHA256withRSA"),

    /**
     * RSASSA-PKCS-v1_5 using SHA-384
     */
    RS384("RS384", "RSASSA-PKCS-v1_5 using SHA-384", "RSA", "SHA384withRSA"),

    /**
     * RSASSA-PKCS-v1_5 using SHA-512
     */
    RS512("RS512", "RSASSA-PKCS-v1_5 using SHA-512", "RSA", "SHA512withRSA");


    private final String value;
    private final String description;
    private final String familyName;
    private final String jcaName;

    SignatureAlgorithm(String value, String description, String familyName, String jcaName) {
        this.value = value;
        this.description = description;
        this.familyName = familyName;
        this.jcaName = jcaName;
    }

    /**
     * Returns the JWA algorithm name constant.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the JWA algorithm description.
     */
    public String getDescription() {
        return description;
    }


    public String getFamilyName() {
        return familyName;
    }

    /**
     * Returns the name of the JCA algorithm used to compute the signature.
     */
    public String getJcaName() {
        return jcaName;
    }


    /**
     * Returns {@code true} if the enum instance represents an HMAC signature algorithm, {@code false}
     * otherwise.
     */
    public boolean isHmac() {
        return name().startsWith("HS");
    }

    /**
     * Returns {@code true} if the enum instance represents an RSA public/private key pair signature
     * algorithm, {@code false} otherwise.
     */
    public boolean isRsa() {
        return getDescription().startsWith("RSASSA");
    }

    public String getSignatureType() {
        if (isHmac()) return "HMAC";
        if (isRsa())  return "RSA";
        return "NONE";
    }
}

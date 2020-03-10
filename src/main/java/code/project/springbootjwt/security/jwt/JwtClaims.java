package code.project.springbootjwt.security.jwt;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JwtClaims represent a set of &quot;claims&quot;
 * <p>
 * This is ultimately a JSON map and any values can be added to it, but JWT standard names are
 * provided as getters and setters for convenience.
 * </p>
 */
public class JwtClaims extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = -6710936279543045432L;
    private static final String SCOPE = "scope";

    public JwtClaims() {
    }

    public JwtClaims(Map<? extends String, ?> claims) {
        super(claims);
    }

    public String getIssuer() {
        return containsKey(Claims.ISS.getValue()) ? (String) get(Claims.ISS.getValue()) : null;
    }

    public JwtClaims setIssuer(String iss) {
        put(Claims.ISS.getValue(), iss);
        return this;
    }

    public String getSubject() {
        return containsKey(Claims.SUB.getValue()) ? (String) get(Claims.SUB.getValue()) : null;
    }

    public JwtClaims setSubject(String sub) {
        setValue(Claims.SUB.getValue(), sub);
        return this;
    }

    public String getAudience() {
        return containsKey(Claims.AUD.getValue()) ? (String) get(Claims.AUD.getValue()) : null;
    }

    public JwtClaims setAudience(String aud) {
        setValue(Claims.AUD.getValue(), aud);
        return this;
    }

    public long getExpiration() {
        return containsKey(Claims.EXP.getValue()) ? ((Number) get(Claims.EXP.getValue())).longValue()
                : 0l;
    }

    public JwtClaims setExpiration(long exp) {
        setValue(Claims.EXP.getValue(), exp);
        return this;
    }

    public long getNotBefore() {
        return containsKey(Claims.NBF.getValue()) ? ((Number) get(Claims.NBF.getValue())).longValue()
                : 0l;
    }

    public JwtClaims setNotBefore(long nbf) {
        setValue(Claims.NBF.getValue(), nbf);
        return this;
    }

    public long getIssuedAt() {
        return containsKey(Claims.IAT.getValue()) ? ((Number) get(Claims.IAT.getValue())).longValue()
                : 0l;
    }

    public JwtClaims setIssuedAt(long iat) {
        setValue(Claims.IAT.getValue(), iat);
        return this;
    }

    public String getID() {
        return containsKey(Claims.JTI.getValue()) ? (String) get(Claims.JTI.getValue()) : null;
    }

    public JwtClaims setID(String jti) {
        setValue(Claims.JTI.getValue(), jti);
        return this;
    }

    public String getScope() {
        return containsKey(SCOPE) ? (String) get(SCOPE) : null;
    }

    public JwtClaims setScope(String scope) {
        setValue(SCOPE, scope);
        return this;
    }

    public String getDomain() {
        return containsKey(Claims.DOMAIN.getValue()) ? (String) get(Claims.DOMAIN.getValue()) : null;
    }

    public JwtClaims setDomain(String domain) {
        setValue(Claims.DOMAIN.getValue(), domain);
        return this;
    }

    protected void setValue(String name, Object v) {
        if (v == null) {
            remove(name);
        } else {
            put(name, v);
        }
    }

}

package code.project.springbootjwt.security.jwt;

public class ValidationResponse {

    private boolean isValid;
    private JwtClaims claims;

    public ValidationResponse(boolean isValid, JwtClaims claims) {
        this.isValid = isValid;
        this.claims = claims;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public JwtClaims getClaims() {
        return claims;
    }

    public void setClaims(JwtClaims claims) {
        this.claims = claims;
    }
}

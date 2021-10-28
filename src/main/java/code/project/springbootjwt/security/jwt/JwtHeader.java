package code.project.springbootjwt.security.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class JwtHeader implements Serializable {

    private static final long serialVersionUID = -415228395196698216L;

    @JsonProperty("alg")
    private SignatureAlgorithm algorithm;

    @JsonProperty("typ")
    private String type;

    @JsonProperty("cty")
    private String contentType;

    @JsonProperty("kid")
    private String keyId;


    public JwtHeader() {
    }

    public JwtHeader(SignatureAlgorithm algorithm, String type, String contentType, String keyId) {
        this.algorithm = algorithm;
        this.type = type;
        this.contentType = contentType;
        this.keyId = keyId;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    public String getType() {
        return type;
    }

    public String getContentType() {
        return contentType;
    }

    public String getKeyId() {
        return keyId;
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}

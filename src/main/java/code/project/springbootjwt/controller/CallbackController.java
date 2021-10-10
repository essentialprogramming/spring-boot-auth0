package code.project.springbootjwt.controller;

import code.project.springbootjwt.controller.model.TokenRequest;
import code.project.springbootjwt.controller.model.TokenResponse;
import code.project.springbootjwt.security.util.URLUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static code.project.springbootjwt.security.SecurityConstants.*;

@SuppressWarnings("unused")
@Controller
public class CallbackController {

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    protected void getCallback(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        handle(req, res);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    protected void postCallback(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        handle(req, res);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String code = request.getParameter("code");
            String redirectUri = request.getParameter("redirect_uri");
            String accessToken = getAccessToken(code, request);
            response.sendRedirect(redirectUri + "?token=" + accessToken);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.sendRedirect(TASKS_URL);
        }
    }

    private static String getAccessToken(String code, HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TokenRequest> tokenRequest = new HttpEntity<>(
                new TokenRequest(
                        "authorization_code",
                        OAUTH0_CLIENT,
                        OAUTH0_CLIENT_SECRET,
                        code,
                        URLUtil.getBaseUrl(request) + TASKS_URL),
                headers);

        TokenResponse tokenResponse = restTemplate.postForObject(
                OAUTH0_TOKEN_ENDPOINT,
                tokenRequest,
                TokenResponse.class);

        assert tokenResponse != null;
        return tokenResponse.getAccessToken();
    }

}

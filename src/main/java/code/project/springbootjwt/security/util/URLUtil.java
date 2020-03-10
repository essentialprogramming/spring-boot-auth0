package code.project.springbootjwt.security.util;

import javax.servlet.http.HttpServletRequest;

public class URLUtil {

    public static String getBaseUrl(HttpServletRequest request) {
        String baseURL = request.getScheme() + "://" + request.getServerName();
        if ((request.getScheme().equals("http") && request.getServerPort() != 80) ||
                (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            baseURL += ":" + request.getServerPort();
        }
        return baseURL;
    }
}

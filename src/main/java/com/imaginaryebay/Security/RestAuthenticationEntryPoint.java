package com.imaginaryebay.Security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ben on 7/13/16.
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(final HttpServletRequest request , final HttpServletResponse response, final AuthenticationException authenticationException)
            throws IOException
    {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");

    }

}

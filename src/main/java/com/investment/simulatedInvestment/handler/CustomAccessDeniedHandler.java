package com.investment.simulatedInvestment.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e
    ) throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("<script>alert('권한이 없습니다.'); location.href='/';</script>");

//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        request.getRequestDispatcher("/errorPage").forward(request, response);
    }

}

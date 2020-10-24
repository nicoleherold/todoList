package filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

@WebFilter(filterName="sanitizingFilter", urlPatterns="/*")

public class SanitizingFilter extends HttpFilter {

        //Aus Lösung von Herrn Fischli

        private static final String VALID_PATTERN = "[A-Za-z0-9\\-,.:!?]*";

        public void init(FilterConfig config) throws ServletException {
            System.out.println("SanitizingFilter init!");

        }

        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            if (request.getMethod().equals("POST")){
                request.setCharacterEncoding("UTF-8");
                Enumeration<String> e = request.getParameterNames();
                while (e.hasMoreElements()) {
                    String value = request.getParameter(e.nextElement());
                    if(!value.matches(VALID_PATTERN)){
                        response.sendRedirect("error.html");
                        return;
                    }
                }
            }
            chain.doFilter(request, response);
        }

        public void destroy() {
            System.out.println("Filter destroy!");
        }

    }




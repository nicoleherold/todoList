package model.todo;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter(filterName="sanitizingFilter", urlPatterns="/*")

public class SanitizingFilter extends HttpFilter {

        private static final Logger logger = Logger.getLogger(SanitizingFilter.class.getName());

        public void init(FilterConfig config) {
        }

        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            logger.info("Request URI: " + request.getRequestURI());
            chain.doFilter(request, response);
            logger.info("Response status: " + response.getStatus());
        }

        public void destroy() {
        }

    }




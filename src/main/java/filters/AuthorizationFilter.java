package filters;

import Utils.UrlPath;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
@WebFilter("/*")
public class AuthorizationFilter implements Filter{
    private static final Set<String> PUBLIC_PATH;
    static {
       PUBLIC_PATH = new HashSet<>();
       PUBLIC_PATH.add(UrlPath.LOGIN);
       PUBLIC_PATH.add(UrlPath.REGISTRATION);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if(isPublicPath(uri) || isUserLoggedIn(servletRequest)){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }

   private boolean isUserLoggedIn(ServletRequest servletRequest) { Object user  =((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user!=null;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(e->uri.startsWith(e));
    }
}

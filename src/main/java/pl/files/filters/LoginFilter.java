package pl.files.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/app/*")
public class LoginFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession();

        if(session.getAttribute ("id") != null && (int)session.getAttribute("id") > 0){
            chain.doFilter(req, res);
        } else {
            res.sendRedirect("/login");
        }

//        chain.doFilter(req, res);

    }
}

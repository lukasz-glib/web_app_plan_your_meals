package pl.files.web;

import pl.files.dao.AdminDao;
import pl.files.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        AdminDao adminDao = new AdminDao();
        HttpSession session = req.getSession();
        int id = adminDao.verify(req.getParameter("email"), req.getParameter("password"));
        if (id > 0) {
            Admin admin = adminDao.read(id);
            session.setAttribute("name", admin.getFirstName());
            session.setAttribute("id", id);
            resp.sendRedirect("/app/dashboard");

        } else {
            req.setAttribute("logged", "false");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }

    }
}

package pl.files.web;

import pl.files.dao.AdminDao;
import pl.files.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/edit/password")
public class editPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/editPassword.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.read((Integer) req.getSession().getAttribute("id"));
        String password = req.getParameter("newPassword");
        String passwordRepeat = req.getParameter("newPasswordRepeat");
        if (password != null && password.equals(passwordRepeat)) {
            admin.setPassword(password);
            adminDao.update(admin);
            resp.sendRedirect("/app/dashboard");
        } else {
            req.setAttribute("wrong" , true);
            req.getRequestDispatcher("/editPassword.jsp");
        }

    }
}

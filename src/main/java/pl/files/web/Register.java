package pl.files.web;

import pl.files.dao.AdminDao;
import pl.files.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if ("".equals(name)) {
            req.setAttribute("missingParameter", "imię");
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
        if ("".equals(surname)) {
            req.setAttribute("missingParameter", "nazwisko");
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
        if ("".equals(email)) {
            req.setAttribute("missingParameter", "email");
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
        if ("".equals(password)) {
            req.setAttribute("missingParameter", "hasło");
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }


        AdminDao adminDao = new AdminDao();
        Admin admin = new Admin(name, surname, email, password);
        adminDao.create(admin);
        resp.sendRedirect("/login");


    }
}

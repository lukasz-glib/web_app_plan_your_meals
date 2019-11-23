package pl.files.web;

import pl.files.dao.RecipePlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/details/delete")
public class RecipePlanDelete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("id", req.getParameter("id"));
        req.getRequestDispatcher("/recipePlanDelete.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("confirm") != null) {
            new RecipePlanDao().delete(Integer.parseInt(req.getParameter("confirm")));
        }
        resp.sendRedirect("/app/plan/list");
    }
}

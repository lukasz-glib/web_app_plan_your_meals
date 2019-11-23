package pl.files.web;

import pl.files.dao.PlanDao;
import pl.files.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/app/plan/add")
public class AddPlan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/addPlan.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String description = req.getParameter("description");

        if ("".equals(name)) {
            req.setAttribute("badParameter", "nazwaPlanu");
            getServletContext().getRequestDispatcher("/addPlan.jsp").forward(req, resp);
        }
        if ("".equals(description)) {
            req.setAttribute("badParameter", "opisPlanu");
            getServletContext().getRequestDispatcher("/addPlan.jsp").forward(req, resp);
        }
        PlanDao planDao = new PlanDao();
        Plan plan = new Plan(name, description, (Integer) req.getSession().getAttribute("id"));
        planDao.create(plan);
        resp.sendRedirect("/app/plan/list");
    }
}

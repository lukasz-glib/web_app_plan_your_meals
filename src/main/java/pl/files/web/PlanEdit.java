package pl.files.web;

import pl.files.dao.PlanDao;
import pl.files.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/app/plan/edit")
public class PlanEdit extends HttpServlet {
int id;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

         id = Integer.parseInt(req.getParameter("id"));
        PlanDao planDao = new PlanDao();
        req.setAttribute("plan", planDao.read(id));



        req.getRequestDispatcher("/planEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("planName");
        String description = req.getParameter("planDescription");

        if ("".equals(name)) {
            req.setAttribute("badParameter", "planName");
            getServletContext().getRequestDispatcher("/planEdit.jsp").forward(req, resp);
        }
        if ("".equals(description)) {
            req.setAttribute("badParameter", "planDescription");
            getServletContext().getRequestDispatcher("/planEdit.jsp").forward(req, resp);
        }
            PlanDao planDao = new PlanDao();
            Plan plan = new Plan(id,name, description, (Integer) req.getSession().getAttribute("id"));
            planDao.update(plan);
            resp.sendRedirect("/app/plan/list");

        }
    }


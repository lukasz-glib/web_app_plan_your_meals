package pl.files.web;

import pl.files.dao.PlanDao;
import pl.files.dao.RecipePlanDao;
import pl.files.model.RecipePlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/delete")
public class PlanDelete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("id", req.getParameter("id"));
        req.getRequestDispatcher("/planDelete.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getParameter("confirm"));

        if (req.getParameter("confirm") != null){
            RecipePlanDao recipePlanDao = new RecipePlanDao();
            for(RecipePlan recipePlan : recipePlanDao.findAllRecipePlansForPlan(Integer.parseInt(req.getParameter("confirm")))){
                recipePlanDao.delete(recipePlan.getId());
            }
            new PlanDao().delete(Integer.parseInt(req.getParameter("confirm")));
        }

        resp.sendRedirect("/app/plan/list");

    }
}

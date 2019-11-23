package pl.files.web;

import pl.files.dao.PlanDao;
import pl.files.dao.RecipePlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/details")
public class PlanDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));
        PlanDao planDao = new PlanDao();
        req.setAttribute("plan", planDao.read(id));

        RecipePlanDao recipePlanDao = new RecipePlanDao();
        req.setAttribute("recipePlans", recipePlanDao.findRecipePlansForDisplay(id));

        req.getRequestDispatcher("/planDetails.jsp").forward(req, resp);
    }
}

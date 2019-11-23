package pl.files.web;

import pl.files.dao.PlanDao;
import pl.files.dao.RecipeDao;
import pl.files.model.RecipePlan;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/app/dashboard")
public class Dashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        //WAŻNE USUŃ!!!!!!!!!!!!!!!!!!!!!!!!
        //session.setAttribute("id", 1);
        ////////////////////////////////////////////////

        PlanDao planDao = new PlanDao();
        RecipeDao recipeDao = new RecipeDao();
        int id = (Integer) session.getAttribute("id");

        req.setAttribute("numberOfPlans", planDao.numberOfPlansAdded(id));
        req.setAttribute("numberOfRecipes", recipeDao.numberOfRecipesAdded(id));

        Map<String, ArrayList> recipePlanMap = planDao.finRecentUserPlan(id);
        String planName = (String) recipePlanMap.keySet().stream().toArray()[0];
        req.setAttribute("nameOfPlan", planName);
        List<RecipePlan> recipePlans = recipePlanMap.get(planName);

        req.setAttribute("recipePlans", recipePlans);

        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);

    }
}

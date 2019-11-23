package pl.files.web;

import pl.files.dao.RecipeDao;
import pl.files.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/recipes")
public class Recipes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("recipes", new RecipeDao().findAll());
        req.getRequestDispatcher("recipes.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

 //       System.out.println(req.getParameter("search"));
        List<Recipe> recipes = new RecipeDao().findRecipesByName(req.getParameter("search"));
//        System.out.println(recipes.get(0));

        req.setAttribute("recipes", new RecipeDao().findRecipesByName(req.getParameter("search")));
        req.getRequestDispatcher("/recipesSearch.jsp").forward(req, resp);

    }
}

package pl.files.web;

import pl.files.dao.RecipeDao;
import pl.files.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/recipe/add")
public class AddRecipe extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/addRecipe.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String preparation = req.getParameter("preparation");
        String methodOfPreparing = req.getParameter("methodOfPreparing");
        String ingredients = req.getParameter("ingredients");

        if ("".equals(name)) {
            req.setAttribute("badParameter", "nazwaPrzepisu");
            getServletContext().getRequestDispatcher("/addRecipe.jsp").forward(req, resp);
        }
        if ("".equals(description)) {
            req.setAttribute("badParameter", "opisPrzepisu");
            getServletContext().getRequestDispatcher("/addRecipe.jsp").forward(req, resp);
        }
        if ("".equals(preparation)) {
            req.setAttribute("badParameter", "czasPrzygotowania");
            getServletContext().getRequestDispatcher("/addRecipe.jsp").forward(req, resp);
        }
        if ("".equals(methodOfPreparing)) {
            req.setAttribute("badParameter", "sposóbPrzygotowania");
            getServletContext().getRequestDispatcher("/addRecipe.jsp").forward(req, resp);
        }
        if ("".equals(ingredients)) {
            req.setAttribute("badParameter", "składniki");
            getServletContext().getRequestDispatcher("/addRecipe.jsp").forward(req, resp);
        }

        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = new Recipe(name,ingredients,description,Integer.parseInt(preparation),methodOfPreparing, (Integer) req.getSession().getAttribute("id"));
        recipeDao.create(recipe);
        resp.sendRedirect("/app/recipe/list");
    }
}

package pl.files.web;

import pl.files.dao.RecipeDao;
import pl.files.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/app/edit/recipe")
public class RecipeEdit extends HttpServlet {

    int id;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        id = Integer.parseInt(req.getParameter("id"));
        //String id = req.getParameter("id");
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(id);
        req.setAttribute("recipe", recipe);

        List<String> ingredients = new ArrayList<>();

        for(String ingredient : recipe.getIngredients().split(", ")){
            ingredient = ingredient.trim();
            ingredients.add(ingredient);
        }
        req.setAttribute("ingredients", ingredients);

        getServletContext().getRequestDispatcher("/recipeEdit2.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=utf-8");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String preparation = req.getParameter("preparation");
        String methodOfPreparing = req.getParameter("methodOfPreparing");
        String ingredients = req.getParameter("ingredients");
        //String id1 = req.getParameter("id");

        if ("".equals(name)) {
            req.setAttribute("badParameter", "nazwaPrzepisu");
            getServletContext().getRequestDispatcher("/recipeEdit.jsp").forward(req, resp);
        }
        if ("".equals(description)) {
            req.setAttribute("badParameter", "opisPrzepisu");
            getServletContext().getRequestDispatcher("/recipeEdit.jsp").forward(req, resp);
        }
        if ("".equals(preparation)) {
            req.setAttribute("badParameter", "czasPrzygotowania");
            getServletContext().getRequestDispatcher("/recipeEdit.jsp").forward(req, resp);
        }
        if ("".equals(methodOfPreparing)) {
            req.setAttribute("badParameter", "sposóbPrzygotowania");
            getServletContext().getRequestDispatcher("/recipeEdit.jsp").forward(req, resp);
        }
        if ("".equals(ingredients)) {
            req.setAttribute("badParameter", "składniki");
            getServletContext().getRequestDispatcher("/recipeEdit.jsp").forward(req, resp);
        }

        RecipeDao recipeDao = new RecipeDao();
//        Recipe recipe = new Recipe(name,ingredients,description,Integer.parseInt(preparation),methodOfPreparing, (Integer) req.getSession().getAttribute("id"), (Integer) req.getSession().getAttribute("ID"));
        Recipe recipe = new Recipe(name,ingredients,description,Integer.parseInt(preparation),methodOfPreparing, (Integer) req.getSession().getAttribute("id"), id);
        recipeDao.update(recipe);
        resp.sendRedirect("/app/recipe/list");
    }
}

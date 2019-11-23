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

@WebServlet("/app/recipe/details")
public class RecipeDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(Integer.parseInt(id));
        req.setAttribute("recipe", recipe);

        List<String> ingredients = new ArrayList<>();

        for(String ingredient : recipe.getIngredients().split(", ")){
            ingredient = ingredient.trim();
            ingredients.add(ingredient);
        }
        req.setAttribute("ingredients", ingredients);
        getServletContext().getRequestDispatcher("/recipeDetails.jsp").forward(req, resp);

    }


}

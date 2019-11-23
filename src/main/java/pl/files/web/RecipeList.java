package pl.files.web;


import pl.files.dao.RecipeDao;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/app/recipe/list")
public class RecipeList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        RecipeDao recipeDao = new RecipeDao();

        req.setAttribute("recipeDao", recipeDao.findAllUsersRecipes((Integer) session.getAttribute("id")));
        getServletContext().getRequestDispatcher("/recipeList.jsp").forward(req, resp);

    }


}

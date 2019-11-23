package pl.files.web;

import pl.files.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/recipe/list/delete")
public class RecipeDelete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("id", req.getParameter("id"));
        getServletContext().getRequestDispatcher("/recipeDelete.jsp").forward(req, resp);

        System.out.println(req.getParameter("id"));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getParameter("confirm"));
        if (req.getParameter("confirm") != null) {
            new RecipeDao().delete(Integer.parseInt(req.getParameter("confirm")));
        }
        resp.sendRedirect("/app/recipe/list");

    }
}

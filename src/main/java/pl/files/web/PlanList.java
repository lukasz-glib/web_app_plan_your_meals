package pl.files.web;

import pl.files.dao.PlanDao;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/plan/list")
public class PlanList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        PlanDao planDao = new PlanDao();

        req.setAttribute("planDao", planDao.findAllByUsersPlans((Integer) session.getAttribute("id")));
        getServletContext().getRequestDispatcher("/planList.jsp").forward(req, resp);
    }
}

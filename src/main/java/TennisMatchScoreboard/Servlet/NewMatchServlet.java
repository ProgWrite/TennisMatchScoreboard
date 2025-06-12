package TennisMatchScoreboard.Servlet;


import TennisMatchScoreboard.exceptions.InvalidParameterException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import TennisMatchScoreboard.util.JspHelper;
import TennisMatchScoreboard.util.ValidationUtils;

import java.io.IOException;



@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.getPath("new-match")).forward(request, response);
    }


    //TODO в дальнейшем здесь будет Dto
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String firstPlayerName = req.getParameter("name");
            String secondPlayerName = req.getParameter("name2");

            ValidationUtils.validate(firstPlayerName, secondPlayerName);



    }
}

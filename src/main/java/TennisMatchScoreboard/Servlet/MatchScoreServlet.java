package TennisMatchScoreboard.Servlet;


import TennisMatchScoreboard.service.OngoingMatchService;
import TennisMatchScoreboard.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    OngoingMatchService matchService = OngoingMatchService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String firstPlayerName = (String) session.getAttribute("firstPlayerName");
        String secondPlayerName = (String) session.getAttribute("secondPlayerName");

        req.setAttribute("matchService", matchService);
        req.setAttribute("firstPlayerName", firstPlayerName);
        req.setAttribute("secondPlayerName", secondPlayerName);
        req.getRequestDispatcher(JspHelper.getPath("match-score")).forward(req, resp);
    }
}

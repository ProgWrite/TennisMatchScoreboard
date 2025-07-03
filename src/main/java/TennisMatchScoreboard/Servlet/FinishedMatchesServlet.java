package TennisMatchScoreboard.Servlet;

import TennisMatchScoreboard.entity.Match;
import TennisMatchScoreboard.service.FinishedMatchesPersistenceService;
import TennisMatchScoreboard.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {
    FinishedMatchesPersistenceService persistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("filter_by_player_name");
        List<Match> finishedMatches;

        if (playerName != null && !playerName.isEmpty()) {
            finishedMatches = persistenceService.findFinishedMatchesByPlayerName(playerName);
        }else{
            finishedMatches = persistenceService.getFinishedMatches();
        }

        // TODO надо разобраться когда можно req.setAttribute, а когда нельзя. Может тут и не надо создавать сессию
        HttpSession session = req.getSession();
        session.setAttribute("finishedMatches", finishedMatches);
        req.getRequestDispatcher(JspHelper.getPath("finished-matches")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO надо назвать в jsp также кнопку "name"
        String name = req.getParameter("name");
        resp.sendRedirect(req.getContextPath() + "/matches?filter_by_player_name=" +
                URLEncoder.encode(name, StandardCharsets.UTF_8));
    }
}

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
    private static final int MATCHES_PER_PAGE = 2;


    FinishedMatchesPersistenceService persistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("filter_by_player_name");
        String page = req.getParameter("page");
        List<Match> finishedMatches;

        int currentPage = 1;
        try {
            if (page != null) {
                currentPage = Integer.parseInt(page);
            }
        } catch (NumberFormatException e) {
            //TODO почему он пустой ?)
        }

        if (playerName != null && !playerName.isEmpty()) {
            finishedMatches = persistenceService.findFinishedMatchesByPlayerName(playerName);
        }else{
            finishedMatches = persistenceService.getFinishedMatches();
        }

        // TODO мб в отдельный метод
        int totalMatches = finishedMatches.size();
        int totalPages = (int) Math.ceil((double) totalMatches / MATCHES_PER_PAGE);


        if (currentPage > totalPages){
            currentPage = totalPages;
        }
        if (currentPage < 1){
            currentPage = 1;
        }

        int fromIndex = (currentPage - 1) * MATCHES_PER_PAGE;
        int toIndex = Math.min(fromIndex + MATCHES_PER_PAGE, totalMatches);
        List<Match> matchesForPage = finishedMatches.subList(fromIndex, toIndex);

        // TODO надо разобраться когда можно req.setAttribute, а когда нельзя. Может тут и не надо создавать сессию

        req.setAttribute("matches", matchesForPage);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("playerName", playerName);
        req.getRequestDispatcher(JspHelper.getPath("finished-matches")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO надо назвать в jsp также кнопку "name"
        String name = req.getParameter("name");
        resp.sendRedirect(req.getContextPath() + "/matches?page=1&filter_by_player_name=" +
                URLEncoder.encode(name, StandardCharsets.UTF_8));
    }
}

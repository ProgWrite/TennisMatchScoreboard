package TennisMatchScoreboard.Servlet;

import TennisMatchScoreboard.dto.PaginationDto;
import TennisMatchScoreboard.entity.Match;
import TennisMatchScoreboard.service.FinishedMatchesPersistenceService;
import TennisMatchScoreboard.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {
    private static final int MATCHES_PER_PAGE = 2;


    FinishedMatchesPersistenceService persistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("filter_by_player_name");
        int currentPage = getPageNumber(req.getParameter("page"));

        PaginationDto<Match> pagination = persistenceService.getPaginationPages(
                playerName, currentPage, MATCHES_PER_PAGE);

        // TODO надо разобраться когда можно req.setAttribute, а когда нельзя. Может тут и не надо создавать сессию

        req.setAttribute("matches", pagination.getMatches());
        req.setAttribute("currentPage", pagination.getCurrentPage());
        req.setAttribute("totalPages", pagination.getTotalPages());
        req.setAttribute("playerName", playerName);
        req.getRequestDispatcher(JspHelper.getPath("finished-matches")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        resp.sendRedirect(req.getContextPath() + "/matches?page=1&filter_by_player_name=" +
                URLEncoder.encode(name, StandardCharsets.UTF_8));
    }


    private int getPageNumber(String pageParam) {
        try {
            return pageParam != null ? Integer.parseInt(pageParam) : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

}

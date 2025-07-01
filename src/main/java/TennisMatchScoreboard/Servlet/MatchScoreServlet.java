    package TennisMatchScoreboard.Servlet;


    import TennisMatchScoreboard.entity.OngoingMatch;
    import TennisMatchScoreboard.service.matchScoreCalcultaion.MatchScoreCalculationService;
    import TennisMatchScoreboard.service.OngoingMatchService;
    import TennisMatchScoreboard.util.JspHelper;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import jakarta.servlet.http.HttpSession;

    import java.io.IOException;
    import java.util.UUID;

    @WebServlet("/match-score")
    public class MatchScoreServlet extends HttpServlet {
        OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();


        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpSession session = req.getSession();
            String firstPlayerName = (String) session.getAttribute("firstPlayerName");
            String secondPlayerName = (String) session.getAttribute("secondPlayerName");

            String uuid = req.getParameter("uuid");
            OngoingMatch match = ongoingMatchService.getMatch(UUID.fromString(uuid));

            req.setAttribute("matchService", ongoingMatchService);
            req.setAttribute("firstPlayerName", firstPlayerName);
            req.setAttribute("secondPlayerName", secondPlayerName);
            req.setAttribute("matchScore", match.getMatchScore());
            req.getRequestDispatcher(JspHelper.getPath("match-score")).forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String uuid = req.getParameter("uuid");
            String action = req.getParameter("action");
            OngoingMatch match = ongoingMatchService.getMatch(UUID.fromString(uuid));
            MatchScoreCalculationService calculationService = new MatchScoreCalculationService(match);

            calculationService.gameScoreCalculation(action);

            //TODO потом будет логика до выигрыша
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
        }
    }

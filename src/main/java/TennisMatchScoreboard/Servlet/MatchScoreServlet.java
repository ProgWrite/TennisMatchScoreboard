    package TennisMatchScoreboard.Servlet;


    import TennisMatchScoreboard.entity.Match;
    import TennisMatchScoreboard.entity.OngoingMatch;
    import TennisMatchScoreboard.enums.GameState;
    import TennisMatchScoreboard.service.FinishedMatchesPersistenceService;
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
            // TODO нормально ли что в сервлете устанавливается столько атрибутов?
            session.setAttribute("firstPlayerSets", match.getMatchScore().getFirstPlayerSets());
            session.setAttribute("secondPlayerSets", match.getMatchScore().getSecondPlayerSets());
            session.setAttribute("firstPlayerGames", match.getMatchScore().getFirstPlayerGames());
            session.setAttribute("secondPlayerGames", match.getMatchScore().getSecondPlayerGames());
            session.setAttribute("firstPlayerPoints", match.getMatchScore().getFirstPlayerPoints());
            session.setAttribute("secondPlayerPoints", match.getMatchScore().getSecondPlayerPoints());

            req.getRequestDispatcher(JspHelper.getPath("match-score")).forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String uuid = req.getParameter("uuid");
            String action = req.getParameter("action");
            OngoingMatch match = ongoingMatchService.getMatch(UUID.fromString(uuid));
            MatchScoreCalculationService calculationService = new MatchScoreCalculationService(match);
            calculationService.gameScoreCalculation(action);

            if(match.getGameState() == GameState.GAME_OVER){
                FinishedMatchesPersistenceService finishedMatchService = new FinishedMatchesPersistenceService(match);

                // TODO КОСТЫЛЬ! Этот код временный и виннер должен определяться иначе
                Match savedMatch = Match.builder()
                        .player1(match.getFirstPlayer())
                        .player2(match.getSecondPlayer())
                        .winner(match.getFirstPlayer())
                        .build();
                finishedMatchService.persistFinishedMatch(savedMatch);
                resp.sendRedirect(req.getContextPath() + "game-over");
            }else{
                resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
            }

        }

    }

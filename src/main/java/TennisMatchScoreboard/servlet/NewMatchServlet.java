package TennisMatchScoreboard.servlet;

import TennisMatchScoreboard.dto.CurrentMatchDto;
import TennisMatchScoreboard.entity.OngoingMatch;
import TennisMatchScoreboard.entity.Player;
import TennisMatchScoreboard.exceptions.InvalidParameterException;
import TennisMatchScoreboard.service.OngoingMatchService;
import TennisMatchScoreboard.util.JspHelper;
import TennisMatchScoreboard.util.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;


@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.getPath("new-match")).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrentMatchDto currentMatch = new CurrentMatchDto(
                req.getParameter("name"),
                req.getParameter("name2")
        );

        try {
            ValidationUtils.validate(currentMatch.firstPlayerName(), currentMatch.secondPlayerName());

            HttpSession session = req.getSession();
            session.setAttribute("firstPlayerName", currentMatch.firstPlayerName());
            session.setAttribute("secondPlayerName", currentMatch.secondPlayerName());

            Player firstPlayer = new Player(currentMatch.firstPlayerName());
            Player secondPlayer = new Player(currentMatch.secondPlayerName());

            OngoingMatch match = ongoingMatchService.createNewMatch(firstPlayer, secondPlayer);
            UUID uuid = match.getUuid();
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid.toString());
        } catch (InvalidParameterException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("name", currentMatch.firstPlayerName());
            req.setAttribute("name2", currentMatch.secondPlayerName());
            req.getRequestDispatcher(JspHelper.getPath("new-match")).forward(req, resp);

        }
    }
}

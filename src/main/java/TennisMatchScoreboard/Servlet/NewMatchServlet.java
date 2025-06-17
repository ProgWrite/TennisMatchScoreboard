package TennisMatchScoreboard.Servlet;


import TennisMatchScoreboard.dto.PlayerDto;
import TennisMatchScoreboard.mapper.PlayerMapper;
import TennisMatchScoreboard.service.OngoingMatchService;
import TennisMatchScoreboard.service.PlayerService;
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
    private final PlayerMapper playerMapper = PlayerMapper.getInstance();
    private final PlayerService playerService = new PlayerService(playerMapper);
    private final OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();


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

            HttpSession session = req.getSession();
            session.setAttribute("firstPlayerName", firstPlayerName);
            session.setAttribute("secondPlayerName", secondPlayerName);

            PlayerDto first = new PlayerDto(firstPlayerName);
            PlayerDto second = new PlayerDto(secondPlayerName);

            //TODO думаю это можно сделать более изящно! Мб тут будут исключения
            PlayerDto firstPlayer = playerService.create(first);
            PlayerDto secondPlayer = playerService.create(second);
            ongoingMatchService.createOngoingMatch(firstPlayer, secondPlayer);
            UUID uuid = ongoingMatchService.getMatchId();

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid.toString());

    }
}

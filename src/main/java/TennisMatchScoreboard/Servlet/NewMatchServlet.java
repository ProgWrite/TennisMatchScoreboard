package TennisMatchScoreboard.Servlet;


import TennisMatchScoreboard.dto.PlayerDto;
import TennisMatchScoreboard.mapper.PlayerMapper;
import TennisMatchScoreboard.service.PlayerService;
import TennisMatchScoreboard.util.JspHelper;
import TennisMatchScoreboard.util.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    PlayerMapper playerMapper = PlayerMapper.getInstance();
    PlayerService playerService = new PlayerService(playerMapper);

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

            PlayerDto firstPlayer = new PlayerDto(firstPlayerName);
            PlayerDto secondPlayer = new PlayerDto(secondPlayerName);

            playerService.create(firstPlayer);
            playerService.create(secondPlayer);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.sendRedirect(req.getContextPath() + "/match-score");

    }
}

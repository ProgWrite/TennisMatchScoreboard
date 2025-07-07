<%@ page import="TennisMatchScoreboard.service.OngoingMatchService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String firstPlayerName = (String) session.getAttribute("firstPlayerName");
    String secondPlayerName = (String) session.getAttribute("secondPlayerName");
%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Счет матча | Tennis Scoreboard</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            padding: 40px 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .container {
            background: white;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 100%;
            max-width: 800px;
            position: relative;
            overflow: hidden;
        }

        .container::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 8px;
            background: linear-gradient(90deg, #ff6b6b, #4ecdc4);
        }

        h1 {
            color: #2d3436;
            margin-bottom: 30px;
            font-weight: 600;
            font-size: 28px;
            text-align: center;
        }

        .score-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
        }

        .score-table th {
            background: #6c5ce7;
            color: white;
            padding: 12px;
            text-align: center;
            font-weight: 500;
        }

        .score-table td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
        }

        .score-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .score-table tr:hover {
            background-color: #f1f1f1;
        }

        .player-name {
            font-weight: 500;
            text-align: left;
            padding-left: 20px;
        }

        .player1-name {
            color: #6c5ce7;
        }

        .player2-name {
            color: #00b894;
        }

        .controls {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: 20px;
        }

        .point-button {
            padding: 14px 20px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        .player1-button {
            background: #6c5ce7;
            color: white;
        }

        .player1-button:hover {
            background: #5649c0;
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(108, 92, 231, 0.4);
        }

        .player2-button {
            background: #00b894;
            color: white;
        }

        .player2-button:hover {
            background: #00a383;
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(0, 184, 148, 0.4);
        }

        .back-link {
            display: inline-block;
            margin-top: 30px;
            color: #636e72;
            text-decoration: none;
            font-size: 14px;
            transition: color 0.3s ease;
            text-align: center;
        }

        .back-link:hover {
            color: #2d3436;
            text-decoration: underline;
        }

        @media (max-width: 768px) {
            .container {
                padding: 30px 20px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Счет теннисного матча</h1>

    <table class="score-table">
        <thead>
        <tr>
            <th>Игрок</th>
            <th>Сеты</th>
            <th>Геймы</th>
            <th>Очки</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td class="player-name player1-name"><%= firstPlayerName %></td>
            <td>${matchScore.firstPlayerSets}</td>
            <td>${matchScore.firstPlayerGames}</td>
            <td>${matchScore.firstPlayerPoints}</td>
        </tr>
        <tr>
            <td class="player-name player2-name"><%= secondPlayerName %></td>
            <td>${matchScore.secondPlayerSets}</td>
            <td>${matchScore.secondPlayerGames}</td>
            <td>${matchScore.secondPlayerPoints}</td>
        </tr>
        </tbody>
    </table>

    <div class="controls">
        <form method="post" action="match-score?uuid=${param.uuid}">
            <input type="hidden" name="action" value="player1">
            <button type="submit" class="point-button player1-button">
                <%= firstPlayerName %> выиграл очко
            </button>
        </form>

        <form method="post" action="match-score?uuid=${param.uuid}">
            <input type="hidden" name="action" value="player2">
            <button type="submit" class="point-button player2-button">
                <%= secondPlayerName %> выиграл очко
            </button>
        </form>
    </div>

    <a href="new-match" class="back-link">← Вернуться к созданию матча</a>
</div>

<script>
    // Здесь можно добавить JavaScript-логику, если нужно
    // Например, для динамического обновления счета без перезагрузки страницы
</script>
</body>
</html>


<%@ page import="TennisMatchScoreboard.service.OngoingMatchService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:set var="firstPlayerName" value="${sessionScope.firstPlayerName}" />
<c:set var="secondPlayerName" value="${sessionScope.secondPlayerName}" />


<html>
<head>
    <title>Cчет матча</title>

    <style>
        .game-container {
            display: flex;
            align-items: flex-start;
            gap: 20px;
        }
        table {
            border-collapse: collapse;
            width: 50%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .score-buttons {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-top: 40px;
        }
        .score-button {
            padding: 8px 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .score-button:hover {
            background-color: #45a049;
        }
    </style>



</head>
<body>
<h1>Счет теннисного матча</h1>


<div class="game-container">

    <table>
        <thead>
        <tr>
            <th>Имя игрока</th>
            <th>Сет</th>
            <th>Гейм</th>
            <th>Очко</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${firstPlayerName}</td>
            <td>${matchScore.firstPlayerSets}</td>
            <td>${matchScore.firstPlayerGames}</td>
            <td>${matchScore.firstPlayerPoints}</td>
        </tr>
        <tr>
            <td>${secondPlayerName}</td>
            <td>${matchScore.secondPlayerSets}</td>
            <td>${matchScore.secondPlayerGames}</td>
            <td>${matchScore.secondPlayerPoints}</td>
        </tr>
        </tbody>
    </table>

    <div class="score-buttons">
        <form method="post" action="match-score?uuid=${param.uuid}">
            <input type="hidden" name="action" value="player1">
            <button type="submit"> Игрок ${firstPlayerName} выиграл очко </button>
        </form>

        <form method="post" action="match-score?uuid=${param.uuid}">
            <input type="hidden" name="action" value="player2">
            <button type="submit"> Игрок ${secondPlayerName} выиграл очко</button>
        </form>


    </div>
</div>




<a href="/new-match">Вернуться на страницу создания матча</a>

</body>
</html>

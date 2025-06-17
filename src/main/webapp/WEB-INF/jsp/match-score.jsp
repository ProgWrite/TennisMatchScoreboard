
<jsp:useBean id="matchService" scope="request" type="TennisMatchScoreboard.service.OngoingMatchService"/>


<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.06.2025
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="TennisMatchScoreboard.Servlet.MatchScoreServlet" %>
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
            <td>${matchService.firstPlayerSet}</td>
            <td>${matchService.firstPlayerGame}</td>
            <td>${matchService.firstPlayerPoint}</td>
        </tr>
        <tr>
            <td>${secondPlayerName}</td>
            <td>${matchService.secondPlayerSet}</td>
            <td>${matchService.secondPlayerGame}</td>
            <td>${matchService.secondPlayerPoint}</td>
        </tr>
        </tbody>
    </table>

    <div class="score-buttons">
        <button class="score-button" onclick="addScore()">Score ${firstPlayerName} </button>
        <button class="score-button" onclick="addScore()">Score ${secondPlayerName} </button>
    </div>
</div>




<a href="/new-match">Вернуться на страницу создания матча</a>

</body>
</html>

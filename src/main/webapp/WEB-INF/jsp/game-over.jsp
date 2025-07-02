<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="firstPlayerName" value="${sessionScope.firstPlayerName}" />
<c:set var="secondPlayerName" value="${sessionScope.secondPlayerName}" />
<c:set var="firstPlayerSets" value="${sessionScope.firstPlayerSets}" />
<c:set var="secondPlayerSets" value="${sessionScope.secondPlayerSets}" />
<c:set var="firstPlayerGames" value="${sessionScope.firstPlayerGames}" />
<c:set var="secondPlayerGames" value="${sessionScope.secondPlayerGames}" />
<c:set var="firstPlayerPoints" value="${sessionScope.firstPlayerPoints}" />
<c:set var="secondPlayerPoints" value="${sessionScope.secondPlayerPoints}" />

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Игра окончена | Tennis Scoreboard</title>
    <style>
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            padding: 40px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .container {
            background: white;
            border-radius: 16px;
            padding: 40px;
            width: 100%;
            max-width: 800px;
        }
        h1 {
            text-align: center;
            color: #2d3436;
        }
        .score-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .score-table th, .score-table td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }
        .score-table th {
            background-color: #6c5ce7;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Игра окончена!</h1>
    <h1>Итоговый счет</h1>
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
            <td>${firstPlayerName}</td>
            <td>${firstPlayerSets}</td>
            <td>${firstPlayerGames}</td>
            <td>${firstPlayerPoints}</td>
        </tr>
        <tr>
            <td>${secondPlayerName}</td>
            <td>${secondPlayerSets}</td>
            <td>${secondPlayerGames}</td>
            <td>${secondPlayerPoints}</td>
        </tr>
        </tbody>
    </table>

    <p>
        <a href="/">Вернуться на главную страницу</a>
    </p>
    <p>
        <a href="new-match">Создать новый матч</a>
    </p>
</div>
</body>
</html>
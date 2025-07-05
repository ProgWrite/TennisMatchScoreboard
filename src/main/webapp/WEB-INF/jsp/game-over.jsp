<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="firstPlayerName" value="${sessionScope.firstPlayerName}" />
<c:set var="secondPlayerName" value="${sessionScope.secondPlayerName}" />
<c:set var="matchScore" value="${sessionScope.matchScore}" />

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Игра окончена | Tennis Scoreboard</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            background: white;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 100%;
            max-width: 800px;
            text-align: center;
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
        }

        .tennis-icon {
            width: 80px;
            height: 80px;
            margin: 0 auto 20px;
            background: #fdcb6e;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
            box-shadow: 0 3px 10px rgba(253, 203, 110, 0.3);
        }

        .tennis-icon::before {
            content: "";
            position: absolute;
            width: 70px;
            height: 70px;
            border: 2px solid white;
            border-radius: 50%;
        }

        .score-table {
            width: 100%;
            border-collapse: collapse;
            margin: 30px 0;
            box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
        }

        .score-table th, .score-table td {
            padding: 15px;
            text-align: center;
            border: 1px solid #e0e0e0;
        }

        .score-table th {
            background-color: #6c5ce7;
            color: white;
            font-weight: 500;
            letter-spacing: 0.5px;
        }

        .score-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .score-table tr:hover {
            background-color: #f1f1f1;
        }

        .action-buttons {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: 30px;
        }

        .btn {
            display: inline-block;
            padding: 14px 20px;
            background: #6c5ce7;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            text-align: center;
            box-shadow: 0 4px 10px rgba(108, 92, 231, 0.3);
        }

        .btn:hover {
            background: #5649c0;
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(108, 92, 231, 0.4);
        }

        .btn-secondary {
            background: #636e72;
            box-shadow: 0 4px 10px rgba(99, 110, 114, 0.3);
        }

        .btn-secondary:hover {
            background: #2d3436;
            box-shadow: 0 6px 15px rgba(45, 52, 54, 0.4);
        }

        .winner-banner {
            background: #00b894;
            color: white;
            padding: 10px;
            border-radius: 8px;
            margin: 20px 0;
            font-weight: 500;
            box-shadow: 0 4px 10px rgba(0, 184, 148, 0.3);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="tennis-icon"></div>
    <h1>Игра окончена!</h1>

    <!-- Можно добавить баннер с победителем -->
    <div class="winner-banner">
        Победитель: ${matchScore.firstPlayerSets > matchScore.secondPlayerSets ? firstPlayerName : secondPlayerName}
    </div>

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

    <div class="action-buttons">
        <a href="/" class="btn">Вернуться на главную страницу</a>
        <a href="matches" class="btn btn-secondary">Архив завершенных матчей</a>
        <a href="new-match" class="btn">Создать новый матч</a>
    </div>
</div>
</body>
</html>
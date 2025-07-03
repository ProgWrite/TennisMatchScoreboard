<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Завершенные матчи | Tennis Scoreboard</title>
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
            max-width: 1000px;
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
            background: linear-gradient(90deg, #6c5ce7, #00b894);
        }

        h1 {
            color: #2d3436;
            margin-bottom: 30px;
            font-weight: 600;
            font-size: 28px;
            text-align: center;
        }

        .search-form {
            display: flex;
            gap: 10px;
            margin-bottom: 25px;
            width: 100%;
            max-width: 600px;
        }

        .search-form input {
            flex-grow: 1;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.3s;
        }

        .search-form input:focus {
            outline: none;
            border-color: #6c5ce7;
        }

        .search-form button {
            padding: 12px 20px;
            background: #6c5ce7;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 500;
            transition: background 0.3s;
        }

        .search-form button:hover {
            background: #5649c0;
        }

        .reset-link {
            display: inline-flex;
            align-items: center;
            padding: 12px 15px;
            background: #f1f1f1;
            color: #636e72;
            border-radius: 8px;
            text-decoration: none;
            transition: all 0.3s;
        }

        .reset-link:hover {
            background: #e1e1e1;
            color: #2d3436;
        }

        .matches-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
        }

        .matches-table th {
            background: #6c5ce7;
            color: white;
            padding: 14px;
            text-align: left;
            font-weight: 500;
        }

        .matches-table td {
            padding: 12px 14px;
            border-bottom: 1px solid #eee;
        }

        .matches-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .matches-table tr:hover {
            background-color: #f1f1f1;
        }

        .winner {
            font-weight: 500;
            color: #00b894;
        }

        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
            margin: 30px 0;
        }

        .pagination a, .pagination span {
            padding: 8px 14px;
            border-radius: 6px;
            text-decoration: none;
            transition: all 0.3s;
        }

        .pagination a {
            color: #6c5ce7;
            border: 1px solid #6c5ce7;
        }

        .pagination a:hover {
            background: #6c5ce7;
            color: white;
        }

        .pagination .current-page {
            background: #6c5ce7;
            color: white;
            font-weight: 500;
        }

        .navigation-links {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 20px;
        }

        .navigation-links a {
            color: #636e72;
            text-decoration: none;
            transition: color 0.3s;
        }

        .navigation-links a:hover {
            color: #2d3436;
            text-decoration: underline;
        }

        @media (max-width: 768px) {
            .container {
                padding: 30px 20px;
            }

            .search-form {
                flex-direction: column;
            }

            .matches-table {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Законченные матчи</h1>

    <form class="search-form" action="${pageContext.request.contextPath}/matches" method="get">
        <input type="text" name="filter_by_player_name"
               value="${not empty playerName ? playerName : ''}"
               placeholder="Введите имя игрока">
        <button type="submit">Поиск</button>
        <c:if test="${not empty playerName}">
            <a href="${pageContext.request.contextPath}/matches" class="reset-link">Сбросить</a>
        </c:if>
    </form>

    <table class="matches-table">
        <thead>
        <tr>
            <th>ID матча</th>
            <th>Игрок 1</th>
            <th>Игрок 2</th>
            <th>Победитель</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="match" items="${matches}">
            <tr>
                <td>${match.id}</td>
                <td>${match.player1.name}</td>
                <td>${match.player2.name}</td>
                <td class="winner">${match.winner.name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${totalPages > 1}">
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/matches?page=${currentPage - 1}&filter_by_player_name=${playerName}">
                    ← Назад
                </a>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="pageNum">
                <c:choose>
                    <c:when test="${pageNum == currentPage}">
                        <span class="current-page">${pageNum}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/matches?page=${pageNum}&filter_by_player_name=${playerName}">
                                ${pageNum}
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/matches?page=${currentPage + 1}&filter_by_player_name=${playerName}">
                    Вперед →
                </a>
            </c:if>
        </div>
    </c:if>

    <div class="navigation-links">
        <a href="/">Вернуться на главную страницу</a>
    </div>
</div>
</body>
</html>
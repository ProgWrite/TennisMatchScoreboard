<%@ page import="java.util.List" %>
<%@ page import="TennisMatchScoreboard.entity.Match" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String playerName = (String) request.getAttribute("playerName");
    List<Match> matches = (List<Match>) request.getAttribute("matches");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    String contextPath = request.getContextPath();
%>

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

    <form class="search-form" action="<%= contextPath %>/matches" method="get">
        <input type="text" name="filter_by_player_name"
               value="<%= playerName != null ? playerName : "" %>"
               placeholder="Введите имя игрока">
        <button type="submit">Поиск</button>
        <% if (playerName != null && !playerName.isEmpty()) { %>
        <a href="<%= contextPath %>/matches" class="reset-link">Сбросить</a>
        <% } %>
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
        <% if (matches != null) {
            for (Match match : matches) { %>
        <tr>
            <td><%= match.getId() %></td>
            <td><%= match.getPlayer1().getName() %></td>
            <td><%= match.getPlayer2().getName() %></td>
            <td class="winner"><%= match.getWinner().getName() %></td>
        </tr>
        <%   }
        } %>
        </tbody>
    </table>

    <% if (totalPages != null && totalPages > 1) { %>
    <div class="pagination">
        <% if (currentPage != null && currentPage > 1) { %>
        <a href="<%= contextPath %>/matches?page=<%= currentPage - 1 %>&filter_by_player_name=<%= playerName != null ? playerName : "" %>">
            ← Назад
        </a>
        <% } %>

        <% if (totalPages > 0) {
            for (int pageNum = 1; pageNum <= totalPages; pageNum++) {
                if (pageNum == currentPage) { %>
        <span class="current-page"><%= pageNum %></span>
        <% } else { %>
        <a href="<%= contextPath %>/matches?page=<%= pageNum %>&filter_by_player_name=<%= playerName != null ? playerName : "" %>">
            <%= pageNum %>
        </a>
        <% }
        }
        } %>

        <% if (currentPage != null && currentPage < totalPages) { %>
        <a href="<%= contextPath %>/matches?page=<%= currentPage + 1 %>&filter_by_player_name=<%= playerName != null ? playerName : "" %>">
            Вперед →
        </a>
        <% } %>
    </div>
    <% } %>

    <div class="navigation-links">
        <a href="/">Вернуться на главную страницу</a>
    </div>
</div>
</body>
</html>
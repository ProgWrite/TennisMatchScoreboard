
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Завершенные матчи</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>Законченные матчи</h1>


    <form action="${pageContext.request.contextPath}/matches" method="post">
        <input type="text" name="name" placeholder="Имя игрока" required>
        <button type="submit">Поиск </button>
    </form>


<table>
    <thead>
    <tr>
        <th>ID матча</th>
        <th>Игрок 1</th>
        <th>Игрок 2</th>
        <th>Победитель</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="match" items="${finishedMatches}">
        <tr>
            <td>${match.id}</td>
            <td>${match.player1.name}</td>
            <td>${match.player2.name}</td>
            <td>${match.winner.name}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
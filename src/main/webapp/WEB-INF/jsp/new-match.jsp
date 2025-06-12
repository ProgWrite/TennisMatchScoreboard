<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Новый матч</title>
</head>
<body>
<h1>Новый матч</h1>

      <form action="/new-match" method="post">
        <label for="name">  Имя первого игрока:
          <input type="text" name = "name" id="name">
        </label><br>
        <label for="name2">  Имя второго игрока:
          <input type="text" name = "name2" id="name2">
        </label><br>
        <button type="submit">Начать игру</button>

      </form>


<a href="/">Вернуться на главную страницу</a>
</body>
</html>

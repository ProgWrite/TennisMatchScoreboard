<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Новый матч | Tennis Scoreboard</title>
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
            max-width: 500px;
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

        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #2d3436;
            font-weight: 500;
        }

        input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #dfe6e9;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s ease;
        }

        input:focus {
            border-color: #6c5ce7;
            outline: none;
            box-shadow: 0 0 0 3px rgba(108, 92, 231, 0.2);
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
            width: 100%;
            margin-top: 10px;
            box-shadow: 0 4px 10px rgba(108, 92, 231, 0.3);
        }

        .btn:hover {
            background: #5649c0;
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(108, 92, 231, 0.4);
        }

        .back-link {
            display: inline-block;
            margin-top: 25px;
            color: #636e72;
            text-decoration: none;
            font-size: 14px;
            transition: color 0.3s ease;
        }

        .back-link:hover {
            color: #2d3436;
            text-decoration: underline;
        }

        .player-inputs {
            display: flex;
            gap: 15px;
            margin-bottom: 25px;
        }

        .player-input {
            flex: 1;
        }

        .error-message {
            color: #ff6b6b;
            font-size: 14px;
            margin-top: 5px;
            padding: 5px;
            border-radius: 4px;
        }

        .global-error {
            color: #ff6b6b;
            background-color: rgba(255, 107, 107, 0.1);
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #ff6b6b;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="tennis-icon"></div>
    <h1>Новый матч</h1>

    <form action="new-match" method="post">
        <div class="player-inputs">
            <div class="form-group player-input">
                <label for="name">Игрок 1</label>
                <input type="text" name="name" id="name"
                       placeholder="Введите имя"
                       required
                       value="${not empty name ? name : param.name}">
                <div id="name-error" class="error-message hidden">${errors.name}</div>
            </div>
            <div class="form-group player-input">
                <label for="name2">Игрок 2</label>
                <input type="text" name="name2" id="name2"
                       placeholder="Введите имя"
                       required
                       value="${not empty name2 ? name2 : param.name2}">
                <div id="name2-error" class="error-message hidden">${errors.name2}</div>
            </div>
        </div>

        <div id="global-error" class="global-error hidden">${error}</div>

        <button type="submit" class="btn">Начать матч</button>
    </form>

    <a href="/" class="back-link">← Вернуться на главную</a>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const nameError = document.getElementById('name-error');
        const name2Error = document.getElementById('name2-error');
        const globalError = document.getElementById('global-error');

        if (nameError.textContent.trim() !== '') {
            nameError.classList.remove('hidden');
        }

        if (name2Error.textContent.trim() !== '') {
            name2Error.classList.remove('hidden');
        }

        if (globalError.textContent.trim() !== '') {
            globalError.classList.remove('hidden');
        }
    });
</script>
</body>
</html>
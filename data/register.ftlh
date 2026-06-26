<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация — BookLender</title>
    <link rel="stylesheet" href="/css/books.css">
</head>
<body>
<div class="container">
    <h1>Регистрация</h1>

    <#if error??>
        <p class="error">${error}</p>
    </#if>

    <form action="/register" method="post">
        <div class="form-group">
            <label>Email:</label>
            <input type="email" name="email" required>
        </div>
        <div class="form-group">
            <label>ФИО:</label>
            <input type="text" name="name" required>
        </div>
        <div class="form-group">
            <label>Пароль:</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit">Зарегистрироваться</button>
    </form>
</div>
</body>
</html>
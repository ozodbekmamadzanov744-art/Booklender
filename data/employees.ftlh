<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Сотрудники — BookLender</title>
    <link rel="stylesheet" href="/css/books.css">
</head>
<body>
<div class="container">
    <div class="nav">
        <a href="/books">Книги</a>
        <a href="/employees">Сотрудники</a>
    </div>

    <h1>Сотрудники</h1>
    <table class="books-table">
        <thead>
        <tr>
            <th>#</th>
            <th>ФИО</th>
            <th>Email</th>
            <th>Сейчас читает</th>
            <th>Уже брал</th>
            <th>Подробнее</th>
        </tr>
        </thead>
        <tbody>
        <#list employees as employee>
            <tr class="${employee?item_parity}">
                <td>${employee.id}</td>
                <td>${employee.name}</td>
                <td>${employee.email}</td>
                <td>${employee.currentBooks?size}</td>
                <td>${employee.pastBooks?size}</td>
                <td><a href="/employees/${employee.id}">Открыть</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>

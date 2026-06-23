<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список книг — BookLender</title>
    <link rel="stylesheet" href="/css/books.css">
</head>
<body>
<div class="container">
    <div class="nav">
        <a href="/books">Книги</a>
        <a href="/employees">Сотрудники</a>
    </div>

    <h1>Библиотека</h1>
    <table class="books-table">
        <thead>
        <tr>
            <th>#</th>
            <th>Название</th>
            <th>Автор</th>
            <th>Статус</th>
            <th>Выдана кому</th>
            <th>Подробнее</th>
        </tr>
        </thead>
        <tbody>
        <#list books as book>
            <tr class="${book?item_parity}">
                <td>${book.id}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td class="status ${book.available?string('available', 'taken')}">
                    ${book.available?string('Свободна', 'Выдана')}
                </td>
                <td>
                    <#if book.takenBy??>
                        <#if employeesById[book.takenBy?string]??>
                            <#assign employee = employeesById[book.takenBy?string]>
                            <a href="/employees/${employee.id}">${employee.name}</a>
                        <#else>
                            ID ${book.takenBy}
                        </#if>
                    <#else>
                        —
                    </#if>
                </td>
                <td><a href="/books/${book.id}">Открыть</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>

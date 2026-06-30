<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль — BookLender</title>
    <link rel="stylesheet" href="/css/books.css">
</head>
<body>
<div class="container">
    <h1>Профиль пользователя</h1>

    <div class="employee-profile">
        <p><strong>ФИО:</strong> ${employee.name}</p>
        <p><strong>Email:</strong> ${employee.email}</p>
    </div>

    <#if currentBooks??>
        <h2>Книги на руках (${currentBooks?size} из 2)</h2>
        <#if currentBooks?size == 0>
            <p>Сейчас вы не держите ни одной книги.</p>
        <#else>
            <ul>
                <#list currentBooks as book>
                    <li>
                        ${book.title} (${book.author})
                        <form action="/books/return" method="post" style="display:inline">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button type="submit">Сдать</button>
                        </form>
                    </li>
                </#list>
            </ul>
        </#if>
    </#if>

    <#if availableBooks??>
        <h2>Доступные книги</h2>
        <#if availableBooks?size == 0>
            <p>Свободных книг сейчас нет.</p>
        <#else>
            <ul>
                <#list availableBooks as book>
                    <li>
                        ${book.title} (${book.author})
                        <form action="/books/take" method="post" style="display:inline">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button type="submit">Взять</button>
                        </form>
                    </li>
                </#list>
            </ul>
        </#if>
    </#if>

    <a href="/books">Перейти к списку книг</a>
    <p><a href="/logout">Выйти из системы</a></p>
</div>
</body>
</html>
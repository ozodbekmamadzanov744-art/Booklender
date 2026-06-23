<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${employee.name} — BookLender</title>
    <link rel="stylesheet" href="/css/books.css">
</head>
<body>
<div class="container">
    <div class="nav">
        <a href="/books">Книги</a>
        <a href="/employees">Сотрудники</a>
    </div>

    <a href="/employees">← Назад к сотрудникам</a>

    <div class="employee-profile">
        <h1>${employee.name}</h1>
        <p><strong>Email:</strong> ${employee.email}</p>
    </div>

    <h2 class="section-title">Сейчас читает</h2>
    <#if currentBooks?has_content>
        <table class="books-table">
            <thead>
            <tr>
                <th>#</th>
                <th>Название</th>
                <th>Автор</th>
                <th>Статус</th>
                <th>Подробнее</th>
            </tr>
            </thead>
            <tbody>
            <#list currentBooks as book>
                <tr class="${book?item_parity}">
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td class="status ${book.available?string('available', 'taken')}">
                        ${book.available?string('Свободна', 'Выдана')}
                    </td>
                    <td><a href="/books/${book.id}">Открыть</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <p class="empty-state">Сейчас сотрудник не читает книги.</p>
    </#if>

    <h2 class="section-title">Уже брал</h2>
    <#if pastBooks?has_content>
        <table class="books-table">
            <thead>
            <tr>
                <th>#</th>
                <th>Название</th>
                <th>Автор</th>
                <th>Статус</th>
                <th>Подробнее</th>
            </tr>
            </thead>
            <tbody>
            <#list pastBooks as book>
                <tr class="${book?item_parity}">
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td class="status ${book.available?string('available', 'taken')}">
                        ${book.available?string('Свободна', 'Выдана')}
                    </td>
                    <td><a href="/books/${book.id}">Открыть</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <p class="empty-state">Раньше сотрудник не брал книги.</p>
    </#if>
</div>
</body>
</html>

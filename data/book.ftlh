<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${book.title} — BookLender</title>
    <link rel="stylesheet" href="/css/books.css">
</head>
<body>
<div class="container">
    <a href="/books">← Назад к списку</a>
    <div class="book-detail">
        <img src="${book.image}" alt="${book.title}" class="book-image">
        <div class="book-info">
            <h1>${book.title}</h1>
            <p><strong>Автор:</strong> ${book.author}</p>
            <p>
                <strong>Статус:</strong>
                <span class="status ${book.available?string('available', 'taken')}">
                    ${book.available?string('Свободна', 'Выдана')}
                </span>
            </p>
            <#if book.takenBy??>
                <p><strong>Выдана сотруднику ID:</strong> ${book.takenBy}</p>
            </#if>
        </div>
    </div>
</div>
</body>
</html>
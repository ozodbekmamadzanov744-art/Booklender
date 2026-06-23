package kg.attractor.java.booklender.model;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import kg.attractor.java.booklender.model.Book;
import kg.attractor.java.server.BasicServer;
import kg.attractor.java.server.ContentType;
import kg.attractor.java.server.ResponseCodes;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooklenderServer extends BasicServer {

    private final static Configuration freemarker = initFreeMarker();
    private final List<Book> books = createSampleBooks();

    public BooklenderServer(String host, int port) throws IOException {
        super(host, port);
        registerGet("/books", this::handleBooksList);
        for (Book book : books) {
            final Book b = book;
            registerGet("/books/" + b.getId(), exchange -> handleBookDetail(exchange, b.getId()));
        }
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("data"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleBooksList(HttpExchange exchange) {
        Map<String, Object> model = new HashMap<>();
        model.put("books", books);
        renderTemplate(exchange, "books.ftlh", model);
    }

    private void handleBookDetail(HttpExchange exchange, int bookId) {
        Book book = findBookById(bookId);
        if (book == null) {
            try {
                var data = "404 Not found".getBytes();
                sendByteData(exchange, ResponseCodes.NOT_FOUND, ContentType.TEXT_PLAIN, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("book", book);
        renderTemplate(exchange, "book.ftlh", model);
    }

    private Book findBookById(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            Template temp = freemarker.getTemplate(templateFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
                temp.process(dataModel, writer);
                writer.flush();
                var data = stream.toByteArray();
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private List<Book> createSampleBooks() {
        List<Book> list = new ArrayList<>();
        list.add(new Book(1, "Чистый код",                        "Роберт Мартин", "/images/1.jpg", "available", null));
        list.add(new Book(2, "Паттерны проектирования",           "Банда четырёх", "/images/1.jpg", "taken",     1));
        list.add(new Book(3, "Java: эффективное программирование", "Джошуа Блох",  "/images/1.jpg", "available", null));
        list.add(new Book(4, "Рефакторинг",                       "Мартин Фаулер", "/images/1.jpg", "taken",     2));
        return list;
    }
}
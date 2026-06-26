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
import kg.attractor.java.server.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooklenderServer extends BasicServer {

    private final static Configuration freemarker = initFreeMarker();
    private final List<Book> books = createSampleBooks();
    private final List<Employee> employees = createSampleEmployees();

    public BooklenderServer(String host, int port) throws IOException {
        super(host, port);
        registerGet("/books", this::handleBooksList);
        registerGet("/employees", this::handleEmployeesList);
        registerGet("/register", this::handleRegisterGet);
        registerPost("/register", this::handleRegisterPost);

        for (Book book : books) {
            final Book b = book;
            registerGet("/books/" + b.getId(), exchange -> handleBookDetail(exchange, b.getId()));
        }
        for (Employee employee : employees) {
            final Employee e = employee;
            registerGet("/employees/" + e.getId(), exchange -> handleEmployeeDetail(exchange, e.getId()));
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
        model.put("employeesById", createEmployeesById());
        renderTemplate(exchange, "books.ftlh", model);
    }

    private void handleBookDetail(HttpExchange exchange, int bookId) {
        Book book = findBookById(bookId);
        if (book == null) {
            respondNotFound(exchange);
            return;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("book", book);
        if (book.getTakenBy() != null) {
            model.put("takenByEmployee", findEmployeeById(book.getTakenBy()));
        }
        renderTemplate(exchange, "book.ftlh", model);
    }

    private void handleEmployeesList(HttpExchange exchange) {
        Map<String, Object> model = new HashMap<>();
        model.put("employees", employees);
        renderTemplate(exchange, "employees.ftlh", model);
    }

    private void handleEmployeeDetail(HttpExchange exchange, int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            respondNotFound(exchange);
            return;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("employee", employee);
        model.put("currentBooks", findBooksByIds(employee.getCurrentBooks()));
        model.put("pastBooks", findBooksByIds(employee.getPastBooks()));
        renderTemplate(exchange, "employee.ftlh", model);
    }

    private Book findBookById(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private Employee findEmployeeById(int id) {
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private List<Book> findBooksByIds(List<Integer> ids) {
        List<Book> result = new ArrayList<>();
        if (ids == null) {
            return result;
        }
        for (Integer id : ids) {
            Book book = findBookById(id);
            if (book != null) {
                result.add(book);
            }
        }
        return result;
    }

    private Map<String, Employee> createEmployeesById() {
        Map<String, Employee> result = new HashMap<>();
        for (Employee employee : employees) {
            result.put(String.valueOf(employee.getId()), employee);
        }
        return result;
    }

    private void respondNotFound(HttpExchange exchange) {
        try {
            var data = "404 Not found".getBytes();
            sendByteData(exchange, ResponseCodes.NOT_FOUND, ContentType.TEXT_PLAIN, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private List<Employee> createSampleEmployees() {
        List<Employee> list = new ArrayList<>();

        Employee ivanov = new Employee(1, "Иванов Иван Иванович", "ivanov@office.com", "pass123");
        ivanov.setCurrentBooks(List.of(2));
        ivanov.setPastBooks(List.of(3));
        list.add(ivanov);

        Employee petrova = new Employee(2, "Петрова Мария Сергеевна", "petrova@office.com", "pass456");
        petrova.setCurrentBooks(List.of(4));
        petrova.setPastBooks(List.of());
        list.add(petrova);

        Employee sidorov = new Employee(3, "Сидоров Алексей Николаевич", "sidorov@office.com", "pass789");
        sidorov.setCurrentBooks(List.of());
        sidorov.setPastBooks(List.of(1, 2));
        list.add(sidorov);

        return list;
    }

    private void handleRegisterGet(HttpExchange exchange) {
        renderTemplate(exchange, "register.ftlh", new HashMap<>());
    }

    private void handleRegisterPost(HttpExchange exchange) {
        String body = getBody(exchange);
        Map<String, String> params = Utils.parseUrlEncoded(body, "&");

        String email = params.get("email");
        String name = params.get("name");
        String password = params.get("password");

        boolean exists = employees.stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(email));

        if (exists) {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "Пользователь с таким email уже зарегистрирован!");
            renderTemplate(exchange, "register.ftlh", model);
            return;
        }

        int newId = employees.size() + 1;
        Employee newEmployee = new Employee(newId, name, email, password);
        employees.add(newEmployee);

        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        renderTemplate(exchange, "register-success.ftlh", model);
    }
}

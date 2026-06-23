package kg.attractor.java.booklender.model;

import java.util.List;
import java.util.ArrayList;

public class Employee {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Integer> currentBooks;
    private List<Integer> pastBooks;

    public Employee() {
        this.currentBooks = new ArrayList<>();
        this.pastBooks = new ArrayList<>();
    }

    public Employee(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.currentBooks = new ArrayList<>();
        this.pastBooks = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Integer> getCurrentBooks() { return currentBooks; }
    public void setCurrentBooks(List<Integer> currentBooks) { this.currentBooks = currentBooks; }

    public List<Integer> getPastBooks() { return pastBooks; }
    public void setPastBooks(List<Integer> pastBooks) { this.pastBooks = pastBooks; }
}
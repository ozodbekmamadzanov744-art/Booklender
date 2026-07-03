package kg.attractor.java.booklender.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String image;
    private String status;
    private Integer takenBy;
    private String description;

    public Book() {}

    public Book(int id, String title, String author, String image, String status, Integer takenBy) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.status = status;
        this.takenBy = takenBy;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getTakenBy() { return takenBy; }
    public void setTakenBy(Integer takenBy) { this.takenBy = takenBy; }

    public boolean isAvailable() {
        return "available".equalsIgnoreCase(status);
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
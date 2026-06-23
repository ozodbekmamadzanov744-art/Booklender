package kg.attractor.java;

import kg.attractor.java.booklender.model.BooklenderServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new BooklenderServer("localhost", 9899).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

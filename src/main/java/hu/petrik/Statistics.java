package hu.petrik;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Statistics {
    private List<Book> books;
    private final Connection connection;
    private Scanner sc;

    public Statistics() throws SQLException {
        sc = new Scanner(System.in);
        connection = DriverManager.getConnection("jdbc:mysql://root@localhost:3306/konyvtar-0303");
        books = getAll();
        feladat();
    }

    private void feladat() {
        System.out.printf("500 oldalnál hosszabb könyvek sázma: %d\n", books.stream().filter(e -> e.getPage_count() > 500).count());
        if (books.stream().anyMatch(e -> e.getPublish_year() < 1950)) {
            System.out.println("Van 1950-nél régebbi könyv");
        } else System.out.println("Nincs 1950-nél régebbi könyv");

        Book longest = books.stream().max(Comparator.comparingInt(Book::getPage_count)).orElse(null);
        System.out.printf("Leghosszabb könyv:\nSzerző: %s\nCím: %s\nKiadás éve: %d\nOldalszám: %d\n", longest.getAuthor(), longest.getTitle(), longest.getPublish_year(), longest.getPage_count());

        Map<String, Integer> count = new HashMap<String, Integer>();
        String maxAuth = "nincs";
        int max = 0;

        for (Book e : books) {
            if (count.containsKey(e.getAuthor())) count.put(e.getAuthor(), count.get(e.getAuthor()) + 1);
            else count.put(e.getAuthor(), 1);
            if (count.get(e.getAuthor()) > max) {
                max = count.get(e.getAuthor());
                maxAuth = e.getAuthor();
            }
        }

        System.out.printf("A legtöbb könyvvel rendelkező szerző: %s\n", maxAuth);

        System.out.print("Adja meg egy könyv címét: ");
        String input = sc.nextLine().toLowerCase();
        boolean search = false;
        for (Book e : books) {
            if (e.getTitle().toLowerCase().equals(input)) {
                search = true;
                System.out.println("A megadott könyv szerzője: " + e.getAuthor());
                break;
            }
        }
        if (!search) System.out.println("Nincs ilyen könyv");
    }

    public List<Book> getAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM books");
        List<Book> list = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String title = result.getString("title");
            String author = result.getString("author");
            int publish_year = result.getInt("publish_year");
            int page_count = result.getInt("page_count");
            Date created_at = result.getDate("created_at");
            Date updated_at = result.getDate("updated_at");
            list.add(new Book(id, title, author, publish_year, page_count, created_at, updated_at));
        }
        return list;
    }

    public boolean create(Book book) throws SQLException {
        PreparedStatement s = connection.prepareStatement("INSERT INTO books(title, author, publish_year, page_count, created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?)");
        s.setString(1, book.getTitle());
        s.setString(2, book.getAuthor());
        s.setInt(3, book.getPublish_year());
        s.setInt(4, book.getPage_count());
        s.setDate(5, book.getCreated_at());
        s.setDate(6, book.getUpdated_at());
        return s.executeUpdate() == 1;
    }

    public boolean change(Book book) throws SQLException {
        PreparedStatement s = connection.prepareStatement("UPDATE books SET title = ?, author = ?, publish_year = ?, page_count = ?, created_at = ?, updated_at = ?");
        s.setString(1, book.getTitle());
        s.setString(2, book.getAuthor());
        s.setInt(3, book.getPublish_year());
        s.setInt(4, book.getPage_count());
        s.setDate(5, book.getCreated_at());
        s.setDate(6, book.getUpdated_at());
        return s.executeUpdate() == 1;
    }

    public boolean delete(int id) throws SQLException {
        PreparedStatement s = connection.prepareStatement("DELETE FROM books WHERE id = ?");
        s.setInt(1, id);
        return s.executeUpdate() == 1;
    }
}

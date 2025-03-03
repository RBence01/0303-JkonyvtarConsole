package hu.petrik;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Statistics s = new Statistics();
        } catch (SQLException e) {
            System.out.println("Nem sikerült csatlakozni!");
        }
    }
}
package pl.coderslab;

import pl.coderslab.UserDAO.UserDAO;
import pl.coderslab.models.User;

import java.sql.*;
import java.util.Scanner;

public class UserAdmin {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop?serverTimezone=UTC", "root",
                "coderslab")) {
            Statement statement = connection.createStatement();

            UserDAO dao = new UserDAO();
            System.out.println(dao.findAll());
            System.out.println();

            chooseOption();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void chooseOption() {
        UserDAO dao = new UserDAO();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Wybierz jedną z opcji: ");
            System.out.println("* add -> dodanie użytkownika");
            System.out.println("* edit -> edycja użytkownika");
            System.out.println("* delete -> usunięcie użytkownika");
            System.out.println("* quit -> zakończenie programu");
            System.out.print("Mój wybór: ");
            String desc = scanner.next();

            switch (desc.toLowerCase()) {
                case "quit": {
                    System.out.println("Koniec programu.");
                    return;
                }
                case "add": {
                    User user = new User(askForName(), askForMail(), askForPassword());
                    dao.create(user);
                    System.out.println();
                    System.out.println(dao.findAll());
                    System.out.println();
                    break;
                }
                case "edit": {
                    User user = new User(askForID(), askForName(), askForMail(), askForPassword());
                    dao.update(user);
                    System.out.println();
                    System.out.println(dao.findAll());
                    System.out.println();
                    break;
                }
                case "delete": {
                    int userID = askForID();
                    dao.delete(userID);
                    System.out.println();
                    System.out.println(dao.findAll());
                    System.out.println();
                }
                default: {
                    System.out.println("Niepoprawny wybór - spróbuj jeszcze raz");
                }
            }
            System.out.println();
        }
    }

    public static int askForID() {
        Scanner scanner = new Scanner(System.in);

        Integer userID = null;
        do {
            System.out.print("Wprowadź id użytkownika: ");
            String token = scanner.next();
            try {
                userID = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawny format - wprowadź ponownie.");
            }
        } while (userID == null);
        return userID;
    }

    public static String askForName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wprowadź nazwę użytkownika: ");
        String name = scanner.next();
        return name;
    }

    public static String askForMail() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wprowadź adres e-mail: ");
        String mail = scanner.next();
        return mail;
    }

    public static String askForPassword() {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wprowadź hasło: ");
        String pasw = scanner.next();
        return pasw;
    }
}

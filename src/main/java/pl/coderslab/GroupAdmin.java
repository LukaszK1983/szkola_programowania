package pl.coderslab;

import pl.coderslab.UserDAO.GroupDAO;
import pl.coderslab.models.Group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GroupAdmin {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop?serverTimezone=UTC", "root",
                "coderslab")) {
            Statement statement = connection.createStatement();

            GroupDAO dao = new GroupDAO();
            System.out.println(dao.findAll());
            System.out.println();

            chooseOption();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void chooseOption() {
        GroupDAO dao = new GroupDAO();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Wybierz jedną z opcji: ");
            System.out.println("* add -> dodanie grupy");
            System.out.println("* edit -> edycja grupy");
            System.out.println("* delete -> usunięcie grupy");
            System.out.println("* quit -> zakończenie programu");
            System.out.print("Mój wybór: ");
            String desc = scanner.next();

            switch (desc.toLowerCase()) {
                case "quit": {
                    System.out.println("Koniec programu.");
                    return;
                }
                case "add": {
                    Group group = new Group(askForUserID(), askForName());
                    dao.create(group);
                    System.out.println();
                    System.out.println(dao.findAll());
                    System.out.println();
                    break;
                }
                case "edit": {
                    Group group = new Group(askForID(), askForUserID(), askForName());
                    dao.update(group);
                    System.out.println();
                    System.out.println(dao.findAll());
                    System.out.println();
                    break;
                }
                case "delete": {
                    int groupID = askForID();
                    dao.delete(groupID);
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

        Integer groupID = null;
        do {
            System.out.print("Wprowadź id grupy: ");
            String token = scanner.next();
            try {
                groupID = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawny format - wprowadź ponownie.");
            }
        } while (groupID == null);
        return groupID;
    }

    public static int askForUserID() {
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
        System.out.print("Wprowadź nazwę grupy: ");
        String name = scanner.next();
        return name;
    }
}

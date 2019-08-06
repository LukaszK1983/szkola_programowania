package pl.coderslab;

import pl.coderslab.UserDAO.ExerciseDAO;
import pl.coderslab.models.Exercise;

import java.sql.*;
import java.util.Scanner;

public class ExerciseAdmin {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop?serverTimezone=UTC", "root",
                "coderslab")) {
            Statement statement = connection.createStatement();

            ExerciseDAO dao = new ExerciseDAO();
            System.out.println(dao.findAll());
            System.out.println();

            chooseOption();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void chooseOption() {
        ExerciseDAO dao = new ExerciseDAO();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Wybierz jedną z opcji: ");
            System.out.println("* add -> dodanie zadania");
            System.out.println("* edit -> edycja zadania");
            System.out.println("* delete -> usunięcie zadania");
            System.out.println("* quit -> zakończenie programu");
            System.out.print("Mój wybór: ");
            String desc = scanner.next();

            switch (desc.toLowerCase()) {
                case "quit": {
                    System.out.println("Koniec programu.");
                    return;
                }
                case "add": {
                    Exercise exercise = new Exercise(askForTitle(), askForDescription());
                    dao.create(exercise);
                    System.out.println();
                    System.out.println(dao.findAll());
                    System.out.println();
                    break;
                }
                case "edit": {
                    Exercise exercise = new Exercise(askForID(), askForTitle(), askForDescription());
                    dao.update(exercise);
                    System.out.println();
                    System.out.println(dao.findAll());
                    System.out.println();
                    break;
                }
                case "delete": {
                    int exerciseID = askForID();
                    dao.delete(exerciseID);
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

        Integer exerciseID = null;
        do {
            System.out.print("Wprowadź id zadania: ");
            String token = scanner.next();
            try {
                exerciseID = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawny format - wprowadź ponownie.");
            }
        } while (exerciseID == null);
        return exerciseID;
    }

    public static String askForTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wprowadź tytuł zadania: ");
        String title = scanner.next();
        return title;
    }

    public static String askForDescription() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wprowadź treść zadania: ");
        String description = scanner.next();
        return description;
    }
}

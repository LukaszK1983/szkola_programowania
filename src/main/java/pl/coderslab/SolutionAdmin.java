package pl.coderslab;

import pl.coderslab.UserDAO.ExerciseDAO;
import pl.coderslab.UserDAO.SolutionDAO;
import pl.coderslab.UserDAO.UserDAO;
import pl.coderslab.models.Solution;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.sql.*;
import java.util.Scanner;

public class SolutionAdmin {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop?serverTimezone=UTC", "root",
                "coderslab")) {
            Statement statement = connection.createStatement();

            chooseOption();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void chooseOption() {
        SolutionDAO solutionDAO = new SolutionDAO();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Wybierz jedną z opcji: ");
            System.out.println("* add -> przypisywanie zadań do użytkowników");
            System.out.println("* view -> przeglądanie rozwiązań danego użytkownika");
            System.out.println("* quit -> zakończenie programu");
            System.out.print("Mój wybór: ");
            String desc = scanner.next();

            switch (desc.toLowerCase()) {
                case "quit": {
                    System.out.println("Koniec programu.");
                    return;
                }
                case "add": {
                    UserDAO userDAO = new UserDAO();
                    System.out.println(userDAO.findAll());
                    System.out.println();

                    int userID = askForUserID();

                    ExerciseDAO exerciseDAO = new ExerciseDAO();
                    System.out.println(exerciseDAO.findAll());
                    System.out.println();

                    int exerciseID = askForExerciseID();
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = formatter.format(date);

                    Solution solution = new Solution(exerciseID, userID, strDate, null, null);
                    solutionDAO.create(solution);
                    System.out.println();
                    System.out.println(solutionDAO.findAll());
                    System.out.println();
                    break;
                }
                case "view": {
                    int userID = askForUserID();

                    System.out.println();
                    System.out.println(solutionDAO.findAllByUserId(userID));
                    System.out.println();
                    break;
                }
                default: {
                    System.out.println("Niepoprawny wybór - spróbuj jeszcze raz");
                }
            }
            System.out.println();
        }
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

    public static int askForExerciseID() {
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
}

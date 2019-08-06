package pl.coderslab;

import pl.coderslab.UserDAO.ExerciseDAO;
import pl.coderslab.UserDAO.SolutionDAO;
import pl.coderslab.models.Solution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class AddingSolutions {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop?serverTimezone=UTC", "root",
                "coderslab")) {
            Statement statement = connection.createStatement();
            int userID = Integer.parseInt(args[0]);
            System.out.println("Wprowadzone ID użytkownika: " + userID);

            SolutionDAO solutionDAO = new SolutionDAO();
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Wybierz jedną z opcji: ");
                System.out.println("* add -> dodawanie rozwiązania");
                System.out.println("* view -> przeglądanie swoich rozwiązań");
                System.out.println("* quit -> zakończenie programu");
                System.out.print("Mój wybór: ");
                String desc = scanner.next();

                switch (desc.toLowerCase()) {
                    case "quit": {
                        System.out.println("Koniec programu.");
                        return;
                    }
                    case "add": {
                        ExerciseDAO exerciseDAO = new ExerciseDAO();
                        System.out.println(exerciseDAO.findExercisesWithoutSolution(userID));
                        System.out.println();

                        int exerciseID = askForExerciseID();

                        boolean checkQuery = exerciseDAO.findExercisesWithSolutionOfUser(userID, exerciseID);

                        if (!checkQuery) {
                            Date date = Calendar.getInstance().getTime();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String strDate = formatter.format(date);

                            String description = askForDescription();

                            Solution solution = new Solution(exerciseID, userID, strDate, null, description);
                            System.out.println(solutionDAO.create(solution));
                            System.out.println();
                            break;
                        } else {
                            System.out.println("Do wybranego zadania użytkownik dodał już rozwiązanie.");
                        }
                    }
                    case "view": {
                        System.out.println();
                        System.out.println(solutionDAO.findAllByUserId(userID));
                        System.out.println();
                        break;
                    }
                    default: {
                        System.out.println("Niepoprawny wybór - spróbuj jeszcze raz");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int askForExerciseID() {
        Scanner scanner = new Scanner(System.in);

        Integer exerciseID = null;
        do {
            System.out.print("Podaj ID zadania, dla którego chcesz dodać rozwiązanie: ");
            String token = scanner.next();
            try {
                exerciseID = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawny format - wprowadź ponownie.");
            }
        } while (exerciseID == null);
        return exerciseID;
    }

    public static String askForDescription() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wprowadź rozwiązanie zadania: ");
        String mail = scanner.next();
        return mail;
    }
}

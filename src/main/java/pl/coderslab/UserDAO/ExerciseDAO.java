package pl.coderslab.UserDAO;

import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static pl.coderslab.ConnectionManager.getConnection;

public class ExerciseDAO {
    private static final String CREATE_EXERCISE_QUERY = "INSERT INTO exercise(title, description) VALUES (?, ?)";
    private static final String READ_EXERCISE_QUERY = "SELECT title, description FROM exercise where id = ?";
    private static final String UPDATE_EXERCISE_QUERY = "UPDATE exercise SET title = ?, description = ? where id = ?";
    private static final String DELETE_EXERCISE_QUERY = "DELETE FROM exercise WHERE id = ?";
    private static final String FIND_ALL_EXERCISES_QUERY = "SELECT id, title, description FROM exercise";
    private static final String FIND_ALL_EXERCISES_WITHOUT_SOLUTION_QUERY = "SELECT e.id, title, e.description FROM exercise e LEFT JOIN solution s ON e.id = s.exercise_id WHERE s.exercise_id != (SELECT s.exercise_id FROM solution WHERE s.user_id = ? GROUP BY s.exercise_id) AND s.user_id != ? OR S.exercise_id IS NULL";
    private static final String FIND_EXERCISE_WITH_SOLUTION_OF_USER_QUERY = "SELECT e.id, title, e.description FROM exercise e LEFT JOIN solution s ON e.id = s.exercise_id WHERE s.user_id = ? AND s.exercise_id = ?";

    public Exercise create(Exercise exercise) {
        try  {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise read(int exerciseID) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(READ_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, exerciseID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(exerciseID);
                exercise.setTitle(rs.getString("title"));
                exercise.setDescription(rs.getString("description"));
                return exercise;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Exercise exercise) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_EXERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int exerciseId) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Exercise> findAll() {
        try {
            Connection conn = getConnection();
            List<Exercise> exercises = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));

                exercises.add(exercise);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Exercise> findExercisesWithoutSolution(int userID) {
        try {
            Connection conn = getConnection();
            List<Exercise> exercises = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISES_WITHOUT_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userID);
            statement.setInt(2, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));

                exercises.add(exercise);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean findExercisesWithSolutionOfUser(int userID, int exerciseID) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_EXERCISE_WITH_SOLUTION_OF_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userID);
            statement.setInt(2, exerciseID);
            ResultSet rs = statement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

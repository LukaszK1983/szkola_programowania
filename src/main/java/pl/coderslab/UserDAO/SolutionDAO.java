package pl.coderslab.UserDAO;

import pl.coderslab.models.Solution;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static pl.coderslab.ConnectionManager.getConnection;

public class SolutionDAO {
    private static final String CREATE_SOLUTION_QUERY = "INSERT INTO solution(exercise_id, user_id, created, updated, description) VALUES (?, ?, ?, ?, ?)";
    private static final String READ_SOLUTION_QUERY = "SELECT exercise_id, user_id, created, updated, description FROM solution where id = ?";
    private static final String UPDATE_SOLUTION_QUERY = "UPDATE solution SET exercise_id = ?, user_id = ?, created = ?, updated = ?, description = ? where id = ?";
    private static final String DELETE_SOLUTION_QUERY = "DELETE FROM solution WHERE id = ?";
    private static final String FIND_ALL_SOLUTIONS_QUERY = "SELECT id, exercise_id, user_id, created, updated, description FROM solution";
    private static final String FIND_ALL_USER_SOLUTIONS_QUERY = "SELECT id, exercise_id, created, updated, description FROM solution WHERE user_id = ?";
    private static final String FIND_ALL_EXERCISE_SOLUTIONS_QUERY = "SELECT id, user_id, created, updated, description FROM solution WHERE exercise_id = ? ORDER BY created ASC";

    public Solution create(Solution solution) {
        try  {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, solution.getExercise_id());
            statement.setInt(2, solution.getUser_id());
            statement.setString(3, solution.getCreated());
            statement.setString(4, solution.getUpdated());
            statement.setString(5, solution.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution read(int solutionID) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(READ_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, solutionID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Solution solution = new Solution();
                solution.setId(solutionID);
                solution.setExercise_id(rs.getInt("exercise_id"));
                solution.setUser_id(rs.getInt("user_id"));
                solution.setCreated(rs.getString("created"));
                solution.setUpdated(rs.getString("updated"));
                solution.setUpdated(rs.getString("description"));
                return solution;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Solution solution) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_SOLUTION_QUERY);
            statement.setInt(1, solution.getExercise_id());
            statement.setInt(2, solution.getUser_id());
            statement.setString(3, solution.getCreated());
            statement.setString(4, solution.getUpdated());
            statement.setString(5, solution.getDescription());
            statement.setInt(6, solution.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int solutionId) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_SOLUTION_QUERY);
            statement.setInt(1, solutionId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Solution> findAll() {
        try {
            Connection conn = getConnection();
            List<Solution> solutions = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_SOLUTIONS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setExercise_id(resultSet.getInt("exercise_id"));
                solution.setUser_id(resultSet.getInt("user_id"));
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setDescription(resultSet.getString("description"));

                solutions.add(solution);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Solution> findAllByUserId(int userID) {
        try {
            Connection conn = getConnection();
            List<Solution> solutions = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_SOLUTIONS_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            Solution solution = new Solution();
            while (resultSet.next()) {
                solution.setId(resultSet.getInt("id"));
                solution.setExercise_id(resultSet.getInt("exercise_id"));
                solution.setUser_id(userID);
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setDescription(resultSet.getString("description"));
                solutions.add(solution);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Solution> findAllByExerciseId(int exerciseID) {
        try {
            Connection conn = getConnection();
            List<Solution> solutions = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISE_SOLUTIONS_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, exerciseID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setExercise_id(exerciseID);
                solution.setUser_id(resultSet.getInt("user_id"));
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setUpdated(resultSet.getString("description"));

                solutions.add(solution);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
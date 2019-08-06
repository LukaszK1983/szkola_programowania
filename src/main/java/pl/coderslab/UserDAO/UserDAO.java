package pl.coderslab.UserDAO;

import pl.coderslab.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static pl.coderslab.ConnectionManager.getConnection;

public class UserDAO {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(name, email, password, passwordSalt) VALUES (?, ?, ?, ?)";
    private static final String READ_USER_QUERY = "SELECT name, email, password, passwordSalt FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET name = ?, email = ?, password = ?, passwordSalt = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY = "SELECT id, name, email, password, passwordSalt FROM users";
    private static final String FIND_ALL_GROUP_USERS_QUERY = "SELECT u.id, u.name, email, password, passwordSalt FROM users u JOIN user_group ug ON u.id = ug.user_id WHERE ug.id = ?";

    public User create(User user) {
        try  {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPasswordSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userID) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(userID);
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPasswordSalt(rs.getString("passwordSalt"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(User user) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPasswordSalt());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int userId) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> findAll() {
        try {
            Connection conn = getConnection();
            List<User> users = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPasswordSalt(resultSet.getString("passwordSalt"));

                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User findAllByGroupId(int groupID) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_GROUP_USERS_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, groupID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPasswordSalt(rs.getString("passwordSalt"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

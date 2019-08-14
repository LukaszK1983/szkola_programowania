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

    public User create(User user) throws SQLException {
        Connection conn = getConnection();
        try  {
            int idx = 0;
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(++idx, user.getName());
            statement.setString(++idx, user.getEmail());
            statement.setString(++idx, user.getPassword());
            statement.setString(++idx, user.getPasswordSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            catchCode(e);
            return user;
        } finally {
            closeConnection(conn);
        }
    }

    public User read(int userID) throws SQLException {
        Connection conn = getConnection();
        try {
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
                User user = new User();
                return user;
            }
        } catch (SQLException e) {
            catchCode(e);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public void update(User user) throws SQLException {
        Connection conn = getConnection();
        try {
            int idx = 0;
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(++idx, user.getName());
            statement.setString(++idx, user.getEmail());
            statement.setString(++idx, user.getPassword());
            statement.setString(++idx, user.getPasswordSalt());
            statement.setInt(++idx, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            catchCode(e);
        } finally {
            closeConnection(conn);
        }
    }

    public boolean delete(int userId) throws SQLException {
        Connection conn = getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            catchCode(e);
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public List<User> findAll() throws SQLException {
        Connection conn = getConnection();
        try {
            List<User> users = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = setUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            catchCode(e);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public User findAllByGroupId(int groupID) throws SQLException {
        Connection conn = getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_GROUP_USERS_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, groupID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                User user = setUser(rs);
                return user;
            } else {
                User user = new User();
                return user;
            }
        } catch (SQLException e) {
            catchCode(e);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                catchCode(e);
            }
        }
    }

    public static void catchCode(SQLException e) {
        e.getMessage();
        e.printStackTrace();
    }

    public static User setUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPasswordSalt(rs.getString("passwordSalt"));
        return user;
    }
}

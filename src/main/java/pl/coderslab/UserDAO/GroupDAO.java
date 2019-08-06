package pl.coderslab.UserDAO;

import pl.coderslab.models.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static pl.coderslab.ConnectionManager.getConnection;

public class GroupDAO {
    private static final String CREATE_GROUP_QUERY = "INSERT INTO user_group(user_id, name) VALUES (?, ?)";
    private static final String READ_GROUP_QUERY = "SELECT user_id, name FROM user_group where id = ?";
    private static final String UPDATE_GROUP_QUERY = "UPDATE user_group SET user_id = ?, name = ? where id = ?";
    private static final String DELETE_GROUP_QUERY = "DELETE FROM user_group WHERE id = ?";
    private static final String FIND_ALL_GROUPS_QUERY = "SELECT id, user_id, name FROM user_group";

    public Group create(Group group) {
        try  {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(CREATE_GROUP_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, group.getUser_id());
            statement.setString(2, group.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                group.setId(resultSet.getInt(1));
            }
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Group read(int groupID) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(READ_GROUP_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, groupID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Group group = new Group();
                group.setId(groupID);
                group.setUser_id(rs.getInt("user_id"));
                group.setName(rs.getString("name"));
                return group;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Group group) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_GROUP_QUERY);
            statement.setInt(1, group.getUser_id());
            statement.setString(2, group.getName());
            statement.setInt(3, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int groupId) {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_GROUP_QUERY);
            statement.setInt(1, groupId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Group> findAll() {
        try {
            Connection conn = getConnection();
            List<Group> groups = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_GROUPS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setUser_id(resultSet.getInt("user_id"));
                group.setName(resultSet.getString("name"));

                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

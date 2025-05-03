package com.teachmeskills.hw.lesson_39_42.repository;

import com.teachmeskills.hw.lesson_39_42.config.DatabaseService;
import com.teachmeskills.hw.lesson_39_42.model.User;
import org.springframework.stereotype.Repository;
import com.teachmeskills.hw.lesson_39_42.config.SQLQuery;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final DatabaseService databaseService;

    @Autowired
    public UserRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Optional<User> getUserById(Long id) {
        Connection connection = databaseService.getConnection();
        try {
            PreparedStatement getUserStatement = connection.prepareStatement(SQLQuery.GET_USER_BY_ID);
            getUserStatement.setLong(1, id);
            ResultSet resultSet = getUserStatement.executeQuery();
            return parseUser(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public List<User> findAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection connection = databaseService.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLQuery.FIND_ALL_USERS)) {
            while (resultSet.next()) {
                parseUser(resultSet).ifPresent(users::add);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public Boolean deleteUser(Long id) {

        try (Connection connection = databaseService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DELETE_USER)) {

            System.out.println("Request to delete user with ID: " + id);
            preparedStatement.setLong(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User with ID has been successfully marked as deleted: " + id);
                return true;
            } else {
                System.out.println("User with ID was not found in the database: " + id);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user with ID: " + id + e.getMessage() + e);
            return false;
        }
    }

    public Optional<Long> createUser(User user) {
        Connection connection = databaseService.getConnection();
        Long userId = null;

        try {
            PreparedStatement createUserStatement = connection.prepareStatement(SQLQuery.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            createUserStatement.setString(1, user.getFirst_name());
            createUserStatement.setString(2, user.getSecond_name());
            createUserStatement.setInt(3, user.getAge());
            createUserStatement.setString(4, user.getEmail());
            createUserStatement.setBoolean(5, false);
            createUserStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            createUserStatement.executeUpdate();
            ResultSet generatedKeys = createUserStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getLong(1);
            }
            return Optional.of(userId);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean updateUser(User user) {
        Connection connection = databaseService.getConnection();
        try {
            PreparedStatement getUserStatement = connection.prepareStatement(SQLQuery.UPDATE_USER);
            getUserStatement.setString(1, user.getFirst_name());
            getUserStatement.setString(2, user.getSecond_name());
            getUserStatement.setInt(3, user.getAge());
            getUserStatement.setString(4, user.getEmail());
            getUserStatement.setBoolean(5, false);
            getUserStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            return getUserStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private Optional<User> parseUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setFirst_name(resultSet.getString("first_name"));
            user.setSecond_name(resultSet.getString("second_name"));
            user.setAge(resultSet.getInt("age"));
            user.setEmail(resultSet.getString("email"));
            user.setDeleted(resultSet.getBoolean("is_deleted"));
            user.setCreated(resultSet.getTimestamp("created"));
            user.setUpdated(resultSet.getTimestamp("updated"));
            return Optional.of(user);
        }
        return Optional.empty();
    }
}

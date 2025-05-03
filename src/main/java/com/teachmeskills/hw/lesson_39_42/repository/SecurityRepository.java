package com.teachmeskills.hw.lesson_39_42.repository;

import com.teachmeskills.hw.lesson_39_42.config.DatabaseService;
import com.teachmeskills.hw.lesson_39_42.config.SQLQuery;
import com.teachmeskills.hw.lesson_39_42.model.Role;
import com.teachmeskills.hw.lesson_39_42.model.Security;
import com.teachmeskills.hw.lesson_39_42.model.User;
import com.teachmeskills.hw.lesson_39_42.model.dto.RegistrationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class SecurityRepository {
    private final DatabaseService databaseService;

    @Autowired
    public SecurityRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Optional<Security> getSecurityById(Long id) {
        try (Connection connection = databaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.GET_SECURITY_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(parseSecurity(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean createSecurity(Security security) {
        try (Connection connection = databaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.CREATE_SECURITY_WEB, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, security.getLogin());
            statement.setString(2, security.getPassword());
            statement.setLong(3, security.getUserId());
            statement.setString(4, security.getRole().toString());
            statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            statement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        security.setId(generatedKeys.getLong(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSecurity(Security security) {
        try (Connection connection = databaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.UPDATE_SECURITY)) {
            statement.setString(1, security.getLogin());
            statement.setString(2, security.getPassword());
            statement.setLong(3, security.getUserId());
            statement.setString(4, security.getRole().toString());
            statement.setLong(5, security.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSecurity(Long id) {
        try (Connection connection = databaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.DELETE_SECURITY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Security parseSecurity(ResultSet resultSet) throws SQLException {
        Security security = new Security();
        security.setId(resultSet.getLong("id"));
        security.setLogin(resultSet.getString("login"));
        security.setPassword(resultSet.getString("password"));
        security.setUserId(resultSet.getLong("user_id"));
        security.setRole(Role.valueOf(resultSet.getString("role")));
        security.setCreated(resultSet.getTimestamp("created"));
        return security;
    }

    public Optional<User> registration(RegistrationRequestDto requestDto) {
        try (Connection connection = databaseService.getConnection()) {
            connection.setAutoCommit(false);

            long userId = -1;
            try (PreparedStatement createUserStatement = connection.prepareStatement(SQLQuery.CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
                createUserStatement.setString(1, requestDto.getFirst_name());
                createUserStatement.setString(2, requestDto.getSecond_name());
                createUserStatement.setInt(3, requestDto.getAge());
                createUserStatement.setString(4, requestDto.getEmail());
                createUserStatement.setBoolean(5, false);
                Timestamp now = new Timestamp(System.currentTimeMillis());
                createUserStatement.setTimestamp(6, now);
                createUserStatement.setTimestamp(7, now);

                createUserStatement.executeUpdate();
                try (ResultSet generatedKeys = createUserStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getLong(1);
                    }
                }
            }

            try (PreparedStatement createSecurityStatement = connection.prepareStatement(SQLQuery.CREATE_SECURITY)) {
                createSecurityStatement.setString(1, requestDto.getLogin());
                createSecurityStatement.setString(2, requestDto.getPassword());
                createSecurityStatement.setLong(3, userId);
                createSecurityStatement.setString(4, Role.USER.toString());
                Timestamp now = new Timestamp(System.currentTimeMillis());
                createSecurityStatement.setTimestamp(5, now);
                createSecurityStatement.setTimestamp(6, now);
                createSecurityStatement.executeUpdate();
            }

            connection.commit();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            User user = new User(
                    userId,
                    requestDto.getFirst_name(),
                    requestDto.getSecond_name(),
                    requestDto.getAge(),
                    requestDto.getEmail(),
                    false,
                    now,
                    now
            );
            return Optional.of(user);

        } catch (Exception e) {
            e.printStackTrace();
            try (Connection connection = databaseService.getConnection()) {
                connection.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        return Optional.empty();
    }
}

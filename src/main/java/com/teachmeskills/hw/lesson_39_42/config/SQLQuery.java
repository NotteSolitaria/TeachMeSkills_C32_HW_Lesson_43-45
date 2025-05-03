package com.teachmeskills.hw.lesson_39_42.config;

public interface SQLQuery {
    String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    String FIND_ALL_USERS = "SELECT * FROM users WHERE is_deleted = false";
    String DELETE_USER =  "UPDATE users SET is_deleted = TRUE WHERE id = ?";
    String UPDATE_USER = "UPDATE users SET first_name=?, second_name=?, age=?, email=?, updated=DEFAULT WHERE id=?";
    String CREATE_USER = "INSERT INTO users (first_name, second_name, age, email, is_deleted, created, updated) VALUES (?, ?, ?, ?, ?, ?, ?)";

    String DELETE_PRODUCT = "DELETE FROM product WHERE id = ?";
    String GET_ALL_PRODUCTS = "SELECT * FROM product";
    String GET_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?";
    String CREATE_PRODUCT = "INSERT INTO product (name, price, created, updated) VALUES (?, ?, ?, ?)";
    String UPDATE_PRODUCT = "UPDATE product SET name = ?, price = ?, updated = ? WHERE id = ?";

    String CREATE_SECURITY = "INSERT INTO security (login, password, user_id, role, created, updated) VALUES (?, ?, ?, ?, ?, ?)";
    String DELETE_SECURITY = "DELETE FROM security WHERE id = ?";
    String GET_SECURITY_BY_ID = "SELECT * FROM security WHERE id = ?";
    String CREATE_SECURITY_WEB = "INSERT INTO security (login, password, user_id, role, created, updated) VALUES (?, ?, ?, ?, ?, ?)";
    String UPDATE_SECURITY = "UPDATE security SET login = ?, password = ?, user_id = ?, role = ? WHERE id = ?";
}

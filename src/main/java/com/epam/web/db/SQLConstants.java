package com.epam.web.db;

public class SQLConstants {
    public static final String SQL_INSERT_USER = "INSERT INTO users (login) VALUES(?)";
    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM users ORDER BY id";
    public static final String SQL_GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login LIKE ? ESCAPE '!'";

    private SQLConstants() {
    }

    public static void main(String[] args) {
        System.out.print(SQL_INSERT_USER);
    }
}

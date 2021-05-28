package com.epam.web.db;

import com.epam.web.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String SQL__FIND_USER_BY_LOGIN =
            "SELECT * FROM user WHERE login=?";

    private static final String SQL__FIND_USER_BY_ID =
            "SELECT * FROM user WHERE id=?";

    private static final String SQL__FIND_ALL_USERS =
            "SELECT * FROM user";

    private static final String SQL_INSERT_USER =
            "INSERT INTO user (login, name, email, password, active, is_admin, language, account) " +
                    "VALUES(?,?,?,?,?,?,?,?)";

    private static final String SQL_UPDATE_USER =
            "UPDATE user SET name=?, email=?, active=?, is_admin=?, language=? WHERE login=?";

    private static final String SQL_UPDATE_USER_ACCOUNT =
            "UPDATE user SET account=? WHERE login=?";

    /**
     * Returns a user with the given identifier.
     *
     * @param id
     *            User identifier.
     * @return User entity.
     */
    public User findUser(Long id) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = conn.prepareStatement(SQL__FIND_USER_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(conn);
            System.err.println(ex.getMessage());
        } finally {
            DBManager.getInstance().commitAndClose(conn);
        }
        return user;
    }

    /**
     * Returns a user with the given login.
     *
     * @param login
     *            User login.
     * @return User entity.
     */
    public User findUserByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = conn.prepareStatement(SQL__FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(conn);
            System.err.println(ex.getMessage());
        } finally {
            DBManager.getInstance().commitAndClose(conn);
        }
        return user;
    }

    /**
     * Return all users.
     *
     * @return List<User>.
     */
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_USERS);
            while (rs.next())
                users.add(mapper.mapRow(rs));
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(conn);
            System.err.println(ex.getMessage());
        } finally {
            DBManager.getInstance().commitAndClose(conn);
        }
        return users;
    }

    /**
     * Update user.
     * @param user
     *            user to update.
     * @param param
     *            data or account to update.
     */
    public void updateUser(User user, String param) {
        Connection conn = null;
        try {
            conn = DBManager.getInstance().getConnection();
            if (param == "create") {
                addUser(conn, user);
            }
            if (param == "data") {
                updateUser(conn, user);
            }
            if (param == "account") {
                updateUserAccount(conn, user);
            }
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(conn);
            System.err.println(ex.getMessage());
        } finally {
            DBManager.getInstance().commitAndClose(conn);
        }
    }

    // //////////////////////////////////////////////////////////
    // Entity access methods (for transactions)
    // //////////////////////////////////////////////////////////

    /**
     * Update user.
     * @param user
     *            user to update.
     * @throws SQLException
     */
    public void addUser(Connection conn, User user) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = null;
        int k = 1;
        pstmt.setString(k++, user.getLogin());
        pstmt.setString(k++, user.getName());
        pstmt.setString(k++, user.getEmail());
        pstmt.setString(k++, user.getPassword());
        pstmt.setInt(k++, (user.getActive() ? 1 : 0));
        pstmt.setInt(k++, (user.getAdmin() ? 1 : 0));
        pstmt.setString(k++, user.getLanguage());
        pstmt.setBigDecimal(k, user.getAccount());
        if (pstmt.executeUpdate() > 0) {
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        }
        rs.close();
        pstmt.close();
    }

    /**
     * Update user.
     * @param user
     *            user to update.
     * @throws SQLException
     */
    public void updateUser(Connection conn, User user) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_USER);
        int k = 1;
        pstmt.setString(k++, user.getName());
        pstmt.setString(k++, user.getEmail());
        pstmt.setInt(k++, (user.getActive() ? 1 : 0));
        pstmt.setInt(k++, (user.getAdmin() ? 1 : 0));
        pstmt.setString(k++, user.getLanguage());
        pstmt.setString(k, user.getLogin());
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Update user account.
     * @param user
     *            user to update.
     * @throws SQLException
     */
    public void updateUserAccount(Connection conn, User user) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_USER_ACCOUNT);
        pstmt.setBigDecimal(1, user.getAccount());
        pstmt.setString(2, user.getLogin());
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Extracts a user from the result set row.
     */
    private static class UserMapper implements EntityMapper<User> {

        @Override
        public User mapRow(ResultSet rs) {
            try {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setAccount(rs.getBigDecimal("account"));
                if (rs.getInt("active") == 1) {
                    user.setActive(true);
                } else {
                    user.setActive(false);
                }
                user.setEmail(rs.getString("email"));
                user.setLanguage(rs.getString("language"));
                if (rs.getInt("is_admin") == 1) {
                    user.setAdmin(true);
                } else {
                    user.setAdmin(false);
                }
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

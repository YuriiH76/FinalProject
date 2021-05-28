package com.epam.web.db;

//import org.apache.log4j.Logger;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBManager {
//    private static final Logger log = Logger.getLogger(DBManager.class);

    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    private static DBManager dbManager;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (dbManager == null)
            dbManager = new DBManager();
        return dbManager;
    }

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in your
     * WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return A DB connection.
     */
//    public Connection getConnection() throws SQLException {
//        Connection con = null;
//        try {
//            Context initContext = new InitialContext();
//            Context envContext  = (Context)initContext.lookup("java:/comp/env");
//
//            // ST4DB - the name of data source
//            DataSource ds = (DataSource)envContext.lookup("jdbc/ST4DB");
//            con = ds.getConnection();
//        } catch (NamingException ex) {
//            log.error("Cannot obtain a connection from the pool", ex);
//        }
//        return con;
//    }
    public Connection getConnection() {
        Connection conn = null;
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(getURL());
            conn.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
//            log.error("Cannot obtain a connection ", e);
        }

        return conn;
    }

    private String getURL() {
        String urlConnection = "";
        Properties prop = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/app.properties")) {
            prop.load(input);
            urlConnection = prop.getProperty("connection.url", "");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return urlConnection;
    }


    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Commits and close the given connection.
     *
     * @param conn Connection to be committed and closed.
     */
    public void commitAndClose(Connection conn) {
        try {
            conn.commit();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     * @param conn Connection to be rollbacked and closed.
     */
    public void rollbackAndClose(Connection conn) {
        try {
            conn.rollback();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}

//    public void insertUser(User user) {
//        ResultSet rs = null;
//        try (Connection conn = getConnection(getURL());
//             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
//            pstmt.setString(1, user.getLogin());
//            if (pstmt.executeUpdate() > 0) {
//                rs = pstmt.getGeneratedKeys();
//                if (rs.next()) {
//                    user.setId(rs.getInt(1));
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        } finally {
//            close(rs);
//        }
//    }
//
////    public void insertTeam(Team team) {
////        ResultSet rs = null;
////
////        try (Connection conn = getConnection(getURL());
////             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SQL_INSERT_TEAM, Statement.RETURN_GENERATED_KEYS)) {
////            pstmt.setString(1, team.getName());
////            if (pstmt.executeUpdate() > 0) {
////                rs = pstmt.getGeneratedKeys();
////                if (rs.next()) {
////                    team.setId(rs.getInt(1));
////                }
////            }
////        } catch (SQLException e) {
////            System.err.println(e.getMessage());
////        } finally {
////            close(rs);
////        }
////    }
//
//    public List<User> findAllUsers() {
//        List<User> users = new ArrayList<>();
//        try (Connection conn = getConnection(getURL());
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(SQLConstants.SQL_FIND_ALL_USERS)) {
//            while (rs.next()) {
//                users.add(mapUser(rs));
//            }
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
//        return users;
//    }
//
//    public User getUser(String login) {
//        User user = null;
//        ResultSet rs = null;
//
//        try (Connection conn = getConnection(getURL());
//             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SQL_GET_USER_BY_LOGIN)) {
//            pstmt.setString(1, "%" + escapeForLike(login) + "%");
//            rs = pstmt.executeQuery();
//            if (rs.next()) {
//                user = mapUser(rs);
//            }
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        } finally {
//            close(rs);
//        }
//        return user;
//    }
//
//    private User mapUser(ResultSet rs) throws SQLException {
//        User user = new User();
//        user.setId(rs.getInt("id"));
//        user.setLogin(rs.getString("login"));
//        return user;
//    }
//
////    public List<Team> findAllTeams() {
////        List<Team> teams = new ArrayList<>();
////
////        try (Connection conn = getConnection(getURL());
////             Statement stmt = conn.createStatement();
////             ResultSet rs = stmt.executeQuery(SQLConstants.SQL_FIND_ALL_TEAMS)) {
////            while (rs.next()) {
////                teams.add(mapTeam(rs));
////            }
////        } catch (SQLException e) {
////            System.err.println(e.getMessage());
////        }
////        return teams;
////    }
//
////    public Team getTeam(String name) {
////        Team team = null;
////        ResultSet rs = null;
////
////        try (Connection conn = getConnection(getURL());
////             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SQL_GET_TEAM_BY_NAME)) {
////            pstmt.setString(1, "%" + escapeForLike(name) + "%");
////            rs = pstmt.executeQuery();
////            if (rs.next()) {
////                team = mapTeam(rs);
////            }
////        } catch (SQLException e) {
////            System.err.println(e.getMessage());
////        } finally {
////            close(rs);
////        }
////        return team;
////    }
//
////    public void deleteTeam(Team team) {
////        if (team == null) {
////            return;
////        }
////        Connection conn = null;
////        PreparedStatement pstmt = null;
////
////        try {
////            conn = getConnection(getURL());
////            pstmt = conn.prepareStatement(SQLConstants.SQL_DELETE_TEAM_BY_NAME);
////            pstmt.setString(1, team.getName());
////            pstmt.executeUpdate();
////        } catch (SQLException e) {
////            System.err.println(e.getMessage());
////        } finally {
////            close(pstmt);
////            close(conn);
////        }
////    }
//
////    public void updateTeam(Team team) {
////        if (team == null) {
////            return;
////        }
////        PreparedStatement pstmt = null;
////        Connection conn = null;
////
////        try {
////            conn = getConnection(getURL());
////            pstmt = conn.prepareStatement(SQLConstants.SQL_UPDATE_TEAM_NAME);
////            pstmt.setString(1, team.getName());
////            pstmt.setInt(2, team.getId());
////            pstmt.executeUpdate();
////        } catch (SQLException e) {
////            System.err.println(e.getMessage());
////        } finally {
////            close(pstmt);
////            close(conn);
////        }
////    }
//
////    private void addTeamForUser(Connection conn, int userId, int teamId) {
////        PreparedStatement pstmt = null;
////        try {
////            pstmt = conn.prepareStatement(SQLConstants.SQL_INSERT_TEAM_FOR_USER);
////            pstmt.setInt(1, userId);
////            pstmt.setInt(2, teamId);
////            pstmt.executeUpdate();
////        } catch (SQLException e) {
////            System.err.println(e.getMessage());
////        } finally {
////            close(pstmt);
////        }
////    }
//
////    public void setTeamsForUser(User user, Team... teams) {
////        Connection conn = null;
////        try {
////            conn = getConnection(getURL());
////            conn.setAutoCommit(false);
////            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
////
////            for (Team team : teams) {
////                addTeamForUser(conn, user.getId(), team.getId());
////            }
////
////            conn.commit();
////        } catch (SQLException e) {
////            System.err.println(e.getMessage());
////            try {
////                conn.rollback();
////            } catch (SQLException ex) {
////                System.err.println(ex.getMessage());
////            }
////        } finally {
////            close(conn);
////        }
////    }
//
//
////    public List<Team> getUserTeams(User user) {
////        if (user == null) {
////            return new ArrayList<>();
////        }
////        List<Team> teams = new ArrayList<>();
////        ResultSet rs = null;
////
////        try (Connection conn = getConnection(getURL());
////             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SQL_FIND_USER_TEAMS)) {
////            pstmt.setInt(1, user.getId());
////            rs = pstmt.executeQuery();
////
////            while (rs.next()) {
////                teams.add(mapTeam(rs));
////            }
////        } catch (SQLException e) {
////            System.err.println(e.getMessage());
////        } finally {
////            close(rs);
////        }
////        return teams;
////    }
//
////    private Team mapTeam(ResultSet rs) {
////        Team team = new Team();
////        try {
////            team.setId(rs.getInt("id"));
////            team.setName(rs.getString("name"));
////        } catch (SQLException e) {
////            System.err.println(e.getMessage());
////        }
////        return team;
////    }
//
//    static String escapeForLike(String param) {
//        return param.replace("!", "!!")
//                .replace("%", "!%")
//                .replace("_", "!_")
//                .replace("[", "![");
//    }
//}

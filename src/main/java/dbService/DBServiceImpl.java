package dbService;

import dbService.dao.UserDAO;
import dbService.dataSets.UsersDataSet;

import java.sql.Connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBServiceImpl implements DBService {
    private final Connection connection;
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/javatest";
    static final String USER = "kirill";
    static final String PASS = "4865";

    public DBServiceImpl(){
        this.connection = getPSQLConfiguration();
        UserDAO dao = new UserDAO(this.connection);
        try {
            dao.createTable();
            if(getUserByLogin("admin") == null) {
                dao.insertUser("admin","4865", "admin@admin.com", "admin", "admin");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public UsersDataSet getUserByLogin(String login){
        try {
            return (new UserDAO(connection).getUserByLogin(login));
        }catch (SQLException e){
            return null;
        }
    }

    @Override
    public UsersDataSet getUserById(long id){
        try {
            return (new UserDAO(connection).getUserById(id));
        }catch (SQLException e){
            return null;
        }
    }


    @Override
    public long addUser(String login, String password, String email, String firstName, String lastName) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = new UserDAO(connection);
            dao.createTable();
            dao.insertUser(login, password, email, firstName, lastName);
            connection.commit();
            return dao.getUserByLogin(login).getId();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    private static  Connection getPSQLConfiguration() {
        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return connection;

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package dbService.dao;

import dbService.executor.Executor;
import dbService.dataSets.UsersDataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAO {

    private Executor executor;

    public UserDAO(Connection connection){
        this.executor = new Executor(connection);
    }

    public UsersDataSet getUserById(long id)throws SQLException{
        return executor.execQuery("SELECT * FROM users where id = "+ id, result -> {
            result.next();
            return  new UsersDataSet(result.getLong(1),
                                     result.getString(2),
                                     result.getString(3),
                                     result.getString(4),
                                     result.getString(5),
                                     result.getString(6));
        });
    }

    public UsersDataSet getUserByLogin(String login) throws SQLException{
        return executor.execQuery("SELECT * FROM users WHERE login='" + login+"'", result -> {
            result.next();
            return new UsersDataSet(result.getLong("id"),result.getString("login"), result.getString("password"), result.getString("email"), result.getString("firstName"), result.getString("lastName"));
            });
    }

    public void insertUser(String login, String password, String email, String firstName, String lastName) throws SQLException {
        executor.execUpdate("INSERT INTO users (login, password, email, firstName, lastName) VALUES ('" + login + "', '"+ password +"', '"+ email + "', '"+ firstName +"', '"+ lastName +"')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("CREATE TABLE IF NOT EXISTS users(id SERIAL PRIMARY KEY, login VARCHAR(50),password VARCHAR(50), email VARCHAR(50), firstName VARCHAR(50), lastName VARCHAR(50));");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("DROP TABLE users");
    }



}

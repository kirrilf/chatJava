package dbService;
import dbService.dataSets.UsersDataSet;

import java.sql.SQLException;

public interface DBService {
    public UsersDataSet getUserById(long id) ;

    public UsersDataSet getUserByLogin(String login);

    public long addUser(String login, String password, String email, String firstName, String lastName) throws DBException;

    public void printConnectInfo();
}

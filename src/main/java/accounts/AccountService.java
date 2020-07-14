package accounts;


import dbService.DBException;
import dbService.DBService;
import dbService.DBServiceImpl;
import dbService.dao.UserDAO;
import dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    //private final Map<String, UserProfile> loginToProfile;
    private final DBService dbService;

    private final Map<String, UserProfile> seessionIdToProfile;
    private static AccountService accountService;

    private AccountService(){
        //loginToProfile = new HashMap<>();
        dbService = new DBServiceImpl();
        seessionIdToProfile = new HashMap<>();
    }

    public static AccountService getInstance(){
        if(accountService == null){
            accountService = new AccountService();
        }
        return accountService;
    }

    public void addNewUser(UserProfile userProfile){
        //loginToProfile.put(userProfile.getLogin(), userProfile);
        try {
            dbService.addUser(userProfile.getLogin(), userProfile.getPassword(), userProfile.getEmail(), userProfile.getFirstName(), userProfile.getLastName());
        }catch (DBException e){
            e.printStackTrace();
        }
    }

    public UserProfile getUserByLogin(String login){
        //return loginToProfile.get(login);
        UsersDataSet usersDataSet = dbService.getUserByLogin(login);
        if(usersDataSet == null){
            return null;
        }
        return new UserProfile(usersDataSet.getLogin(), usersDataSet.getPassword(), usersDataSet.getEmail(), usersDataSet.getFirstName(), usersDataSet.getLastName());
    }

    public void deleteUser(String login){
       // loginToProfile.remove(login);
    }

    public UserProfile getUserBySession(String sessionID){
        return seessionIdToProfile.get(sessionID);
    }

    public void addSession(String sessionID, UserProfile userProfile){
        seessionIdToProfile.put(sessionID, userProfile);
    }

    public void deleteSession(String sessionID){
        seessionIdToProfile.remove(sessionID);
    }






}

package sample.services.createNewAccountForUser.impl;

import sample.models.Accounts;
import sample.services.createNewAccountForUser.CreateNewAccount;
import sample.services.database.DatabaseConnection;
import java.sql.Statement;

public class CreateNewAccountImpl implements CreateNewAccount {

    @Override
    public boolean createAccount(int id, String login, String password) {
        Statement statement = null;
        try {
//            Statement statement;
            Accounts accounts = new Accounts(id, login, password, 1);
            String query = "INSERT INTO accounts (user_id, login, password, active) " +
            "VALUES ('"+accounts.getUserId()+"','"+accounts.getLogin()+"','"+accounts.getPassword()+"','"+accounts.getActive()+"')";
            statement = DatabaseConnection.connection.createStatement();
            statement.executeUpdate(query);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                statement.close();
            }catch (Exception e){

            }
        }
        return false;
    }
}

package sample.services.forInsertUserName.impl;

import sample.models.Users;
import sample.services.database.DatabaseConnection;
import sample.services.forInsertUserName.InsertNewUser;

import java.sql.ResultSet;
import java.sql.Statement;

public class InsertNewUserImpl implements InsertNewUser {

    @Override
    public boolean createNewUserAndInsert(String newUserName) {
        try {
            Users newUser = new Users(newUserName);
            String query = "INSERT INTO users (name) VALUES ('"+newUser.getName()+"')";
            Statement statement = DatabaseConnection.connection.createStatement();
            statement.executeUpdate(query);
//            DatabaseConnection.connection.close();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}

package sample.services.forInsertUserName.impl;

import sample.services.database.DatabaseConnection;
import sample.services.forInsertUserName.FindUserIdForAccount;

import java.sql.ResultSet;
import java.sql.Statement;

public class FindUserIdForAccountImpl implements FindUserIdForAccount {

    @Override
    public int findIdUser(String newUserName) {
        int id=0;
        try {
            Statement statement = DatabaseConnection.connection.createStatement();
            String query = "SELECT id FROM users WHERE users.name = '"+newUserName+"'";
            ResultSet rs = statement.executeQuery(query);
//            DatabaseConnection.connection.close();
            while (rs.next()){
                id = rs.getInt("id");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return id;
    }
}

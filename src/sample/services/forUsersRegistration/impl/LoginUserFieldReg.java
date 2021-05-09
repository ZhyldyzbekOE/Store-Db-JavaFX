package sample.services.forUsersRegistration.impl;

import javafx.scene.control.Alert;
import sample.services.database.DBservice;
import sample.services.database.DatabaseConnection;
import sample.services.forUsersRegistration.CorrectUserRegistration;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginUserFieldReg implements CorrectUserRegistration {

    DBservice dBservice = new DatabaseConnection();
//    Statement statement = null;

    @Override
    public boolean userRegistration(String userLoginPassword) {
        String loginUser;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<String> userLogins = new ArrayList<>();
        try {
            statement = DatabaseConnection.connection.createStatement();
            String query = "SELECT login FROM accounts";
            rs = statement.executeQuery(query);
            while (rs.next()){
                loginUser = rs.getString("login");
                userLogins.add(loginUser);
            }
            for (String item:userLogins) {
                if(!item.equals(userLoginPassword)){
                    return true;
                }
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Такой аккаунт уже существует!");
            alert.show();
        }finally {
            try {
                statement.close();
                rs.close();
            }catch (Exception e){

            }
        }
        return false;
    }
}
